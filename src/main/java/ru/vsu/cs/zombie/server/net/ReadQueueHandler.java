package ru.vsu.cs.zombie.server.net;

public class ReadQueueHandler extends QueueHandler {

    public ReadQueueHandler(int threadPoolSize) {
        super(threadPoolSize);
    }

    @Override
    public void run() {
        Session session;
        try {
            while (true) {
                session = sessionQueue.take();
                session.takeFromReadQueue().execute();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
