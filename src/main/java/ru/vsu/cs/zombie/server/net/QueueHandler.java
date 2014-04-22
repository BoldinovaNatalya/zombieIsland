package ru.vsu.cs.zombie.server.net;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class QueueHandler{

    private class WorkerThread implements Runnable {

        private boolean stop = false;

        @Override
        public void run() {
            while (!stop) {
                QueueHandler.this.doWork();
            }
        }

        public void stop() {
            stop = true;
        }
    }

    protected final BlockingQueue<Session> sessionQueue;
    private final ExecutorService threadPool;
    private int threadPoolSize;
    private List<WorkerThread> threads = new ArrayList<WorkerThread>();

    public QueueHandler(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
        this.sessionQueue = new LinkedBlockingQueue<Session>();
        initThreadPool();
    }

    protected abstract void doWork();

    private void initThreadPool() {
        for (int i = 0; i < this.threadPoolSize; i++) {
            threads.add(new WorkerThread());
            threadPool.execute(threads.get(i));
        }
    }

    public void addSessionToProcess(Session session) {
        if (session != null) {
            this.sessionQueue.add(session);
        }
    }

    public void stop() {
        for (WorkerThread thread : threads) {
            thread.stop();
        }
    }
}
