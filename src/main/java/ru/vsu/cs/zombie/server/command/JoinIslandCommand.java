package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.net.Session;

public class JoinIslandCommand extends Command {

    @Override
    public void execute() {
        int id = (Integer)parameters.get("island_id");
        Island island = Island.getIsland(id);
        island.addSession(session);
        session.addToWriteQueue(createResponse());
        if (island.getPlayerCount() == island.getSessions().size()) {
            for (Session session : island.getSessions()) {
                session.addToWriteQueue(Command.createResponse(Command.START_GAME, id));
            }
        }
    }
}
