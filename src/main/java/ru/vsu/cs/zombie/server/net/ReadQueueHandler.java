package ru.vsu.cs.zombie.server.net;

import ru.vsu.cs.zombie.server.command.Command;
import ru.vsu.cs.zombie.server.command.CommandExecutor;

public class ReadQueueHandler extends QueueHandler {

    public ReadQueueHandler(int threadPoolSize) {
        super(threadPoolSize);
    }

    @Override
    public void run() {
        Session session;
        try {
            session = sessionQueue.take();
            Command result = CommandExecutor.execute(session.takeFromReadQueue());
            session.addToWriteQueue(result);
            ZombieServer.getWriter().addSessionToProcess(session);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
