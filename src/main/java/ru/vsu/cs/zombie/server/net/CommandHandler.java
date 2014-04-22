package ru.vsu.cs.zombie.server.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;
import ru.vsu.cs.zombie.server.command.Command;
import ru.vsu.cs.zombie.server.logic.Island;

public class CommandHandler extends SimpleChannelInboundHandler<Command> {

    private static Logger logger = Logger.getLogger(CommandHandler.class.getSimpleName());

    private ZombieServer server;

    public CommandHandler(ZombieServer server) {
        this.server = server;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info(String.format("%s connected", ctx.channel().remoteAddress().toString()));
        server.createSession(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info(String.format("%s disconnected", ctx.channel().remoteAddress().toString()));
        Session session = server.getSession(ctx.channel());
        Island island = session.getIsland();
        if (island != null) {
            island.removeSession(session);
        }
        server.deleteSession(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command command) throws Exception {
        logger.info(command.toString());
        Session session = server.getSession(ctx.channel());
        //command.setSession(session);
        session.process(command);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}


