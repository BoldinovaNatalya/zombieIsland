package ru.vsu.cs.zombie.server.net;

public class WriteQueueHandler extends QueueHandler {

    public WriteQueueHandler(int threadPoolSize) {
        super(threadPoolSize);
    }

    @Override
    public void run() {
        Session session;
        try {
            while(true) {
            session = sessionQueue.take();
            session.getChannel().writeAndFlush(session.takeFromWriteQueue());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
