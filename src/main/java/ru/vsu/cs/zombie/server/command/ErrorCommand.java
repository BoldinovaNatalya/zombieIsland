package ru.vsu.cs.zombie.server.command;

public class ErrorCommand extends Command {

    public static final String MESSAGE = "message";

    @Override
    public void execute() {
        session.addToWriteQueue(Command.create(Command.ERROR));
    }
}
