package ru.vsu.cs.zombie.server.net;

public class ReadQueueHandler extends QueueHandler {

    public ReadQueueHandler(int threadPoolSize) {
        super(threadPoolSize);
    }

    @Override
    protected void doWork() throws Exception{
        commandQueue.take().execute();
    }
}
