package ru.vsu.cs.zombie.server.net;

import io.netty.channel.Channel;
import ru.vsu.cs.zombie.server.command.Command;
import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.objects.Zombie;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Session {

    private Channel channel = null;

    private boolean isAuthorized = false;

    private Island island = null;

    private ZombieServer server;

    private BlockingQueue<Command> readCommandQueue = new LinkedBlockingQueue<Command>();
    private BlockingQueue<Command> writeCommandQueue = new LinkedBlockingQueue<Command>();


    public Session(Channel channel, ZombieServer server) {
        this.channel = channel;
        this.server = server;
    }

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    public Channel getChannel() {
        return channel;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public void addToReadQueue(Command command) {
        readCommandQueue.add(command);
        command.setSession(this);
        server.getReader().addSessionToProcess(this);
    }

    public void addToWriteQueue(Command command) {
        writeCommandQueue.add(command);
        server.getWriter().addSessionToProcess(this);
    }

    public Command takeFromReadQueue() throws InterruptedException {
        return readCommandQueue.take();
    }

    public Command takeFromWriteQueue() throws InterruptedException {
        return writeCommandQueue.take();
    }

    private static Map<Channel, Session> sessions = new HashMap<Channel, Session>();

    public static Session getSession(Channel channel) {
        return sessions.get(channel);
    }

    public static void addSession(Channel channel, ZombieServer server) {
        sessions.put(channel, new Session(channel, server));
    }

    public static void deleteSession(Channel channel) {
        sessions.remove(channel);
    }

}
