package com.webchat.netty;

import com.webchat.mapper.userMapper;
import com.webchat.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * @Description :处理消息的handler
 * 在netty中TextWebSocketFrame是专门为websocket处理文本的对象，frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //用于记录和管理所有客户端的channel
    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端传输过来的消息
        // 获取来自客户端的消息
        String content = msg.text();
        Channel currentChannel = ctx.channel();


        //1. 判断客户端发来的消息
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        System.out.println(dataContent.getAction());
        System.out.println(dataContent.getChatMsg());
        System.out.println(dataContent.getExtand());
//        Integer action = dataContent.getAction();
//        //2. 判断消息类型，根据不同的类型处理不同的业务
//
//        if (action == 1) {
//            //2.1 当websocket第一次open的时候，初始化channel，把用户的channel和userid关联起来
//            String senderId = dataContent.getChatMsg().getSenderID();
//            UserChannelRel.put(senderId, currentChannel);
//        } else if (action == 2) {
//            //2.2 聊天类型的消息，将聊天记录保存到数据库，同时标记消息的签收状态[未签收]
//            ChatMsg chatMsg = dataContent.getChatMsg();
//            String msgText = chatMsg.getMsg();
//            String receiverId = chatMsg.getReceiverID();
//            String senderId = chatMsg.getSenderID();
//            //保存消息到数据库，并且标记为未签收
//        } else if (action == 3) {
//            //2.3 签收消息的类型，针对具体的消息进行签收，修改数据库中对应消息的签收状态[已签收]
//        } else if (action == 4) {
//            //2.4 心跳类型的消息
//
//        }

    }
    /**
     * @Description :
     * 当客户端连接服务器后
     * 获取客户端的channel并且放入ChannelGroup中进行管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //其实下面一行是多余的，当出发handlerRemoved，ChannelGroup会自动移除对应客户端的Channel
//        users.remove(ctx.channel().id().asShortText());
        System.out.println("客户端断开，channel对应的短ID为" + ctx.channel().id().asShortText());
        System.out.println("客户端断开，channel对应的长ID为" + ctx.channel().id().asLongText());
    }
}
