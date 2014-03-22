package ru.vsu.cs.zombie.server.net;

import io.netty.channel.Channel;
import ru.vsu.cs.zombie.server.command.Command;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Session {

    private Channel channel = null;

    public Channel getChannel() {
        return channel;
    }

    private BlockingQueue<Command> readCommandQueue = new LinkedBlockingQueue<Command>();
    private BlockingQueue<Command> writeCommandQueue = new LinkedBlockingQueue<Command>();

    public Session(Channel channel) {
        this.channel = channel;

    }

    public void addToReadQueue(Command command) {
        readCommandQueue.add(command);
        command.setSession(this);
        ZombieServer.getReader().addSessionToProcess(this);
    }

    public void addToWriteQueue(Command command) {
        writeCommandQueue.add(command);
        ZombieServer.getWriter().addSessionToProcess(this);
    }

    public Command takeFromReadQueue() throws InterruptedException {
        return readCommandQueue.take();
    }

    public Command takeFromWriteQueue() throws InterruptedException {
        return writeCommandQueue.take();
    }
}
