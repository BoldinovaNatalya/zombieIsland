package ru.vsu.cs.zombie.server.command;

public abstract class CommandExecutor {
    public static Command execute(Command command) {
        return command;
    }
}
