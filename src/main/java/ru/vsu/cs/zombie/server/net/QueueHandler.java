package ru.vsu.cs.zombie.server.net;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class QueueHandler implements Runnable {
    protected final BlockingQueue<Session> sessionQueue;
    private final ExecutorService threadPool;
    private int threadPoolSize;

    public QueueHandler( int threadPoolSize )
    {
        this.threadPoolSize = threadPoolSize;
        this.threadPool     = Executors.newFixedThreadPool(threadPoolSize);
        this.sessionQueue   = new LinkedBlockingQueue();

        initThreadPool();
    }

    private void initThreadPool()
    {
        for ( int i = 0; i < this.threadPoolSize; i++ )
        {
            this.threadPool.execute(this);
        }
    }

    public void addSessionToProcess( Session session )
    {
        if ( session != null )
        {
            this.sessionQueue.add( session );
        }
    }
}
