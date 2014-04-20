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
            island.start();
            for (Session session : island.getSessions()) {
                Command response = Command.createResponse(Command.START_GAME, id);
                response.parameters.put("base", island.getBase(session).getPosition());
                response.parameters.put("men", island.getMenID(session));
                response.parameters.put("entities", island.getEntitiesID());
                session.addToWriteQueue(response);
            }
        }
    }
}
