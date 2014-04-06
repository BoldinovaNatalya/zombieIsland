package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;

public class GetVisibleCommand extends Command {

    @Override
    public void execute() {
        Integer id = (Integer)parameters.get("man_id");
        Command result;
        Island island = session.getIsland();
        if (island != null && island.playerHaveCharacter(id, session)) {
            result = createResponse();
            result.parameters.put("entities", island.getVisibleEntities(id));
        } else {
            result = new ErrorCommand("Wrong id", id);
        }
        session.addToWriteQueue(result);
    }
}
