package ru.vsu.cs.zombie.server.command;

public class NoSuchCommand extends Exception {
    public NoSuchCommand() {
    }

    public NoSuchCommand(String message) {
        super(message);
    }

    public NoSuchCommand(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchCommand(Throwable cause) {
        super(cause);
    }

    public NoSuchCommand(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
