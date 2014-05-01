package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;

public class JoinIslandCommand extends Command {

    @Override
    public void execute() {
        int id = (Integer)parameters.get("island_id");
        Island island = Island.getIsland(id);
        island.addSession(session);
        session.write(createResponse());
        if (island.getPlayerCount() == island.getSessions().size()) {
            island.start();
        }
    }
}
