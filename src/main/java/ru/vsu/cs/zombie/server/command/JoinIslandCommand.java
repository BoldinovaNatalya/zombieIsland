package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.net.Session;

public class JoinIslandCommand extends Command {

    @Override
    public void execute() {
        int id = (Integer)parameters.get("island_id");
        Island island = Island.getIsland(id);
        island.addSession(session);
        session.write(createResponse());
        int team = 0;
        if (island.getPlayerCount() == island.getSessions().size()) {
            island.start();
            for (Session session : island.getSessions()) {
                Command response = Command.createResponse(Command.START_GAME, id, session);
                response.parameters.put("base", island.getBase(session).getPosition());
                response.parameters.put("men", island.getMenID(session));
                response.parameters.put("entities", island.getEntitiesID());
                response.parameters.put("team", team++);
                session.write(response);
            }
        }
    }
}
