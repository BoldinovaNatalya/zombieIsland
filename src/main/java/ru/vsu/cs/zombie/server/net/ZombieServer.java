package ru.vsu.cs.zombie.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ZombieServer {

    private static final int READ_THREADS = 4;
    private static final int WRITE_THREADS = 2;

    private final int port;
    private ReadQueueHandler reader;
    private WriteQueueHandler writer;

    public ReadQueueHandler getReader() {
        return reader;
    }

    public WriteQueueHandler getWriter() {
        return writer;
    }

    public ZombieServer(int port) {
        this.port = port;
        reader = new ReadQueueHandler(READ_THREADS);
        writer = new WriteQueueHandler(WRITE_THREADS);
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ZombieServerInitializer(this));

            ChannelFuture future = bootstrap.bind(port).sync();

            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
