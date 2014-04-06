package ru.vsu.cs.zombie.server.command;

public class ErrorCommand extends Command {

    public static final String MESSAGE = "message";

    ErrorCommand(String message, int id) {
        this.id = id;
        name = Command.ERROR;
        parameters.put(MESSAGE, message);
    }

    @Override
    public void execute() {
        session.addToWriteQueue(new ErrorCommand("", id));
    }
}
