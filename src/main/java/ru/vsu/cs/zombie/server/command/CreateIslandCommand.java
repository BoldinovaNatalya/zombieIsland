package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;

public class CreateIslandCommand extends Command {

    private static String PLAYERS = "players";

    @Override
    public void execute() {
        Command result;
        try {
            int players = (Integer) parameters.get(PLAYERS);
            if (session.getIsland() == null) {
                Island island = Island.CreateIsland(players);
                island.addSession(session);
                result = Command.create(Command.CREATE_ISLAND);
                session.addToWriteQueue(result);
                if (players == 1) {
                    session.addToWriteQueue(Command.create(Command.START_GAME));
                }
            } else {
                result = Command.create(Command.ERROR);
                result.parameters.put(ErrorCommand.MESSAGE, "This user already has island");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = Command.create(Command.ERROR);
            result.parameters.put(ErrorCommand.MESSAGE, "Json parsing error");
        }
    }
}
