package ru.vsu.cs.zombie.server.command;

public class ErrorCommand extends Command {

    @Override
    public void Execute() {
        session.addToWriteQueue(Command.Create(Command.ERROR));
    }
}
