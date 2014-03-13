package ru.vsu.cs.zombie.server.command;

public class HelloCommand extends Command {

    @Override
    public void Execute() {
        session.addToWriteQueue(Command.Create(Command.HELLO));
    }
}
