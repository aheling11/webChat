package com.webchat.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSserverInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //websocket 基于http协议，所有要有http编解码器
        pipeline.addLast(new HttpServerCodec());
        // 对大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        //对httpMessage进行聚合，聚合成FullHttprequest或FullHttpResponse
        //几乎在所有netty的编程中都会用到这个handler
        pipeline.addLast(new HttpObjectAggregator(1024*64));
        //========================以上是用于支持http协议========================
        //
        /**
         * websocket服务器处理的协议，用于指定客户端连接访问的路由"ws"
         * 此handler会帮助我们处理一些繁重的事情
         * 会帮我们处理握手动作（closing，ping，pong）ping + pong = 心跳
         * 对于websocket来讲，传输以frames为单位，不同数据类型对应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //自定义handler
        pipeline.addLast(new ChatHandler());

    }
}
