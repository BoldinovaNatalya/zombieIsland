package ru.vsu.cs.zombie.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;

public class ZombieServer {

    private final int port;
    private static ReadQueueHandler reader;
    private static WriteQueueHandler writer;
    private static Map<Channel, Session> sessions = new HashMap<Channel, Session>();

    public static Session getSession(Channel channel) {
        return sessions.get(channel);
    }

    public static void addSession(Channel channel) {
        sessions.put(channel, new Session(channel));
    }

    public static void deleteSession(Channel channel) {
        sessions.remove(channel);
    }

    public static ReadQueueHandler getReader() {
        return reader;
    }

    public static WriteQueueHandler getWriter() {
        return writer;
    }

    public ZombieServer(int port) {
        this.port = port;
        reader = new ReadQueueHandler(4);
        writer = new WriteQueueHandler(2);
    }


    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ZombieServerInitializer());

            ChannelFuture future = bootstrap.bind(port).sync();

            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
