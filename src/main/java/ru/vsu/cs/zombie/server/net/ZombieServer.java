package ru.vsu.cs.zombie.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import ru.vsu.cs.zombie.server.command.Command;

import java.util.HashMap;
import java.util.Map;

public class ZombieServer {

    private static final int READ_THREADS = 4;
    private static final int WRITE_THREADS = 2;

    private final int port;
    private ReadQueueHandler reader = new ReadQueueHandler(READ_THREADS);
    private WriteQueueHandler writer = new WriteQueueHandler(WRITE_THREADS);

    private Map<Channel, Session> sessions = new HashMap<Channel, Session>();

    public Session getSession(Channel channel) {
        return sessions.get(channel);
    }

    public void createSession(Channel channel) {
        sessions.put(channel, new Session(channel, this));
    }

    public void deleteSession(Channel channel) {
        sessions.remove(channel);
    }

    public ZombieServer(int port) {
        this.port = port;
    }

    public void process(Command command) {
        reader.addToQueue(command);
    }

    public void write(Command command) {
        writer.addToQueue(command);
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
            reader.stop();
            writer.stop();
        }
    }
}
