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
                result = createResponse();
                if (players == 1) {
                    Command tmp = Command.createResponse(Command.JOIN_ISLAND, id, session);
                    tmp.parameters.put("island_id", Island.getIslands().indexOf(island));
                    session.process(tmp);
                }
            } else {
                result = new ErrorCommand("This user already has an island", id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new ErrorCommand("Json parsing error", id);
        }
        session.write(result);
    }
}
