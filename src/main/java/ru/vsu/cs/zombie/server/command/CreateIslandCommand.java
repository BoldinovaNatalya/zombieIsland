package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;

public class CreateIslandCommand extends Command {

    @Override
    public void execute() {
        Command result;
        try {
            final String playersCount = "players";
            int players = (Integer) parameters.get(playersCount);
            if (session.getIsland() == null) {
                Island island = Island.CreateIsland(players);
                island.addSession(session);
                result = createResponse();
                if (players == 1) {
                    session.addToWriteQueue(Command.createResponse(Command.START_GAME, id));
                }
            } else {
                result = new ErrorCommand("This user already has island", id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new ErrorCommand("Json parsing error", id);
        }
        session.addToWriteQueue(result);
    }
}
