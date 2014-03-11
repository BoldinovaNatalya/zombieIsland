package ru.vsu.cs.zombie.server.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;


public class ZombieServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new HttpResponseEncoder());
        pipeline.addLast(new WebSocketServerProtocolHandler("/websocket"));
        pipeline.addLast("jsonDecoder", new JsonDecoder());
        pipeline.addLast("jsonEncoder", new JsonEncoder());
        pipeline.addLast("commandHandler", new CommandHandler());
    }
}
