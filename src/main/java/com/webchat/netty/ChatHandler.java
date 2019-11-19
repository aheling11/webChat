package com.webchat.netty;

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
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端传输过来的消息
        String content = msg.text();
        System.out.println("received data:" + content);

        clients.writeAndFlush(new TextWebSocketFrame("server received msg at "+ LocalDateTime.now() + ",The msg content is: " + content));

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
        clients.add(ctx.channel());
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //其实下面一行是多余的，当出发handlerRemoved，ChannelGroup会自动移除对应客户端的Channel
//        clients.remove(ctx.channel().id().asShortText());
        System.out.println("客户端断开，channel对应的短ID为" + ctx.channel().id().asShortText());
        System.out.println("客户端断开，channel对应的长ID为" + ctx.channel().id().asLongText());
    }
}
