package ru.vsu.cs.zombie.server.net;

import ru.vsu.cs.zombie.server.command.Command;

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
                try {
                    QueueHandler.this.doWork();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void stop() {
            stop = true;
        }
    }

    protected final BlockingQueue<Command> commandQueue;
    private final ExecutorService threadPool;
    private int threadPoolSize;
    private List<WorkerThread> threads = new ArrayList<WorkerThread>();

    public QueueHandler(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
        this.commandQueue = new LinkedBlockingQueue<Command>();
        initThreadPool();
    }

    protected abstract void doWork() throws Exception;

    private void initThreadPool() {
        for (int i = 0; i < this.threadPoolSize; i++) {
            threads.add(new WorkerThread());
            threadPool.execute(threads.get(i));
        }
    }

    public void addToQueue(Command command) {
        if (command != null) {
            this.commandQueue.add(command);
        }
    }

    public void stop() {
        for (WorkerThread thread : threads) {
            thread.stop();
        }
    }
}
