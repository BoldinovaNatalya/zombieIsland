package ru.vsu.cs.zombie.server.command;

public abstract class CommandExecutor {
    public static Command execute(Command command) throws NoSuchCommand  {
        int id = command.getId();
        Command result = null;
        switch (id) {
            case Command.HELLO:
                result = new Command(Command.HELLO);
                break;
            default:
                throw new NoSuchCommand();
        }
        return result;
    }
}
