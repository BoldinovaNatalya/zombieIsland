package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;

public class GetVisibleCommand extends Command {

    @Override
    public void execute() {
        Integer id = (Integer)parameters.get("man_id");
        Command result;
        Island island = session.getIsland();
        if (island.getMenID(session).contains(id)) {
            result = Command.create(Command.GET_VISIBLE_ENTITIES);
            result.parameters.put("entities", island.getVisibleEntities(id));
        } else {
            result = Command.create(Command.ERROR);
            result.parameters.put(ErrorCommand.MESSAGE, "Wrong id");
        }
        session.addToWriteQueue(result);
    }
}
