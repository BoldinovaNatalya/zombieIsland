package ru.vsu.cs.zombie.server.command;

public class HelloCommand extends Command {

    @Override
    public void execute() {
        session.addToWriteQueue(createResponse());
    }
}
