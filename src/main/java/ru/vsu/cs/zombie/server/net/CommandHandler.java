package ru.vsu.cs.zombie.server.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.vsu.cs.zombie.server.command.Command;

public class CommandHandler extends SimpleChannelInboundHandler<Command> {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(String.format("+ %s", ctx.channel().remoteAddress().toString()));
       Session.addSession(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(String.format("- %s", ctx.channel().remoteAddress().toString()));
        Session.deleteSession(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command command) throws Exception {
        System.out.println(command.toString());
        Session session = Session.getSession(ctx.channel());
        session.addToReadQueue(command);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}


