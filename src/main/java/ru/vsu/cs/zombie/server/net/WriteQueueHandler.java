package ru.vsu.cs.zombie.server.net;

import ru.vsu.cs.zombie.server.command.Command;

public class WriteQueueHandler extends QueueHandler {

    public WriteQueueHandler(int threadPoolSize) {
        super(threadPoolSize);
    }

    @Override
    protected void doWork() throws Exception{
            Command command = commandQueue.take();
            command.getSession().getChannel().writeAndFlush(command);
    }
}
