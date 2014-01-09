package ru.vsu.cs.zombie.server.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.vsu.cs.zombie.server.command.Command;

public class CommandHandler extends SimpleChannelInboundHandler<Command> {

    private Session session = null;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(String.format("+ %s", ctx.channel().remoteAddress().toString()));
        session = new Session(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(String.format("- %s", ctx.channel().remoteAddress().toString()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Command command) throws Exception {
        System.out.println(command.toString());
        session.addToReadQueue(command);
        ZombieServer.getReader().addSessionToProcess(session);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}


