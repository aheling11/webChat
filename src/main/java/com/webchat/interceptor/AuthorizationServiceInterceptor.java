package com.webchat.interceptor;

import com.github.pagehelper.util.StringUtil;
import com.webchat.annotation.PassToken;
import com.webchat.service.UserService;
import com.webchat.utils.HeJSONResult;
import com.webchat.utils.JWTUtil;
import com.webchat.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class AuthorizationServiceInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
        response.setHeader("Access-Control-Allow-Headers", " Origin, X-Requested-With, content-Type, Accept, Authorization");
        response.setHeader("Access-Control-Max-Age", "3600");

        String token = request.getHeader("token");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有passToken注释，有则跳过验证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        if (StringUtil.isEmpty(token)) {
            //token 为空
            returnJson(response);
            return false;
        } else if (JWTUtil.isExpiration(token)) {
            returnJson(response);
            return false;
        } else {
            String username = JWTUtil.getUsername(token);
            if (!userService.queryUsernameIsExist(username)) {
                returnJson(response);
                return false;
            }
            request.setAttribute("code", 0);
            request.setAttribute("CURRENT_USER", username);

        }
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {
    }

    private void returnJson(HttpServletResponse response) throws IOException {
        HeJSONResult result = HeJSONResult.errorTokenMsg("无效token");
        String s = JsonUtils.objectToJson(result);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null ;
        out = response.getWriter();
        out.write(s);
        out.flush();
        out.close();
    }
}