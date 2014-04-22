package ru.vsu.cs.zombie.server.net;

public class WriteQueueHandler extends QueueHandler {

    public WriteQueueHandler(int threadPoolSize) {
        super(threadPoolSize);
    }

    @Override
    protected void doWork() {
        Session session;
        try {
            session = sessionQueue.take();
            session.getChannel().writeAndFlush(session.takeFromWriteQueue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
