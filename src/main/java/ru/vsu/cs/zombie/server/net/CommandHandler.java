package ru.vsu.cs.zombie.server.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;
import ru.vsu.cs.zombie.server.command.Command;
import ru.vsu.cs.zombie.server.logic.objects.Zombie;

public class CommandHandler extends SimpleChannelInboundHandler<Command> {

    private static Logger logger = Logger.getLogger(CommandHandler.class.getSimpleName());

    ZombieServer server;

    public CommandHandler(ZombieServer server) {
        this.server = server;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info(String.format("%s connected", ctx.channel().remoteAddress().toString()));
        Session.addSession(ctx.channel(), server);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info(String.format("%s disconnected", ctx.channel().remoteAddress().toString()));
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


