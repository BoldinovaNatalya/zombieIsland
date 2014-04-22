package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;

public class GetBaseCommand extends Command {

    @Override
    public void execute() {
        Island island = session.getIsland();
        Command response;
        if (island != null) {
            response = createResponse();
            response.parameters.put("base", island.getBase(session));
        } else {
            response = new ErrorCommand("Not connected to any game", id);
        }
        session.write(response);
    }
}
