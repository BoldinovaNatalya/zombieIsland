package ru.vsu.cs.zombie.server.command;

public class ErrorCommand extends Command {

    @Override
    public void execute() {
        session.addToWriteQueue(Command.create(Command.ERROR));
    }
}
