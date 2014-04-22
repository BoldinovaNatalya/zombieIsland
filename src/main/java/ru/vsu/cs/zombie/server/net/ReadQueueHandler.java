package ru.vsu.cs.zombie.server.net;

public class ReadQueueHandler extends QueueHandler {

    public ReadQueueHandler(int threadPoolSize) {
        super(threadPoolSize);
    }

    @Override
    protected void doWork() {
        Session session;
        try {
            session = sessionQueue.take();
            session.takeFromReadQueue().execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
