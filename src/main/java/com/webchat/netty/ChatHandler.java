package com.webchat.netty;

import com.webchat.SpringUtil;
import com.webchat.enums.MsgActionEnum;
import com.webchat.service.ChatService;
import com.webchat.service.UserService;
import com.webchat.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println(content);

        //1. 判断客户端发来的消息
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        Integer action = dataContent.getAction();
        //2. 判断消息类型，根据不同的类型处理不同的业务

        if (action == MsgActionEnum.CONNECT.type ) {
            //2.1 当websocket第一次open的时候，初始化channel，把用户的channel和userid关联起来
            String senderId = dataContent.getChatMsg().getSenderId();
            UserChannelRel.put(senderId, currentChannel);

            // 测试
            for (Channel c : users) {
                System.out.println("uesrsGroup中的 channel ID " +c.id().asLongText());
            }
            UserChannelRel.output();

        } else if (action == MsgActionEnum.CHAT.type) {

            //2.2 聊天类型的消息，将聊天记录保存到数据库，同时标记消息的签收状态[未签收]
            ChatMsg chatMsg = dataContent.getChatMsg();
            String receiverId = chatMsg.getReceiverId();

            //保存消息到数据库，并且标记为未签收
            ChatService chatService = (ChatService) SpringUtil.getBean("chatServiceImpl");
            String msgId = chatService.saveMsg(chatMsg);
            //保存到数据库后，获取到msgId完善chatMsg后就可以传给接收方了
            chatMsg.setMsgId(msgId);

            //发送消息
            Channel receiverChannel = UserChannelRel.get(receiverId);
            if (receiverChannel == null) {
                //receiverChannel为空代表用户离线，推送消息（Jpush，个推，小米推送）
                System.out.println("用户离线");

            } else {
                //当receiverChannel不为空时，还要去channelGroup里查找这个channel是否存在
                Channel findChannel = users.find(receiverChannel.id());
                if (findChannel != null) {
                    //用户在线
                    receiverChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(chatMsg)));
                } else {
                    //用户离线 TODO 推送消息
                    System.out.println("用户离线");
                }
            }


        } else if (action == MsgActionEnum.SIGNED.type) {
            //2.3 签收消息的类型，针对具体的消息进行签收，修改数据库中对应消息的签收状态[已签收] 这里的签收是指客户端自己签收，而不是用户来签收
            ChatService chatService = (ChatService) SpringUtil.getBean("chatServiceImpl");
            //扩展字段在signed类型的消息中，代表需要去签收的消息Id，逗号间隔
            String msgIdsStr = dataContent.getExtand();
            String msgIds[] = msgIdsStr.split(",");

            List<String> msgIdList = new ArrayList<>();
            for (String mid : msgIds) {
                if (StringUtils.isNoneBlank(mid)) {
                    msgIdList.add(mid);
                }
            }

            System.out.println(msgIdList.toString());

            if (msgIdList != null && !msgIdList.isEmpty() && msgIdList.size() > 0) {
                chatService.updateMsgSigned(msgIdList);
            }
        } else if (action == 4) {
            //2.4 心跳类型的消息

        }

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
        users.remove(ctx.channel());
        System.out.println("客户端断开，channel对应的短ID为" + ctx.channel().id().asShortText());
//        System.out.println("客户端断开，channel对应的长ID为" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}
