package ru.vsu.cs.zombie.server.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class ZombieServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast("frameDecoder", new FrameDecoder());
        pipeline.addLast("frameEncoder", new FrameEncoder());
        pipeline.addLast("jsonDecoder", new JsonDecoder());
        pipeline.addLast("jsonEncoder", new JsonEncoder());
        pipeline.addLast("commandHandler", new CommandHandler());
    }
}
