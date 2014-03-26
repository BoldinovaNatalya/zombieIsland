package ru.vsu.cs.zombie.server.command;

public class ErrorCommand extends Command {

    public static final String MESSAGE = "message";

    ErrorCommand(String message) {
        name = Command.ERROR;
        parameters.put(MESSAGE, message);
    }

    @Override
    public void execute() {
        session.addToWriteQueue(Command.create(Command.ERROR));
    }
}
