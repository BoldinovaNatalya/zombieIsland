package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.objects.Entity;

public class GetEntityCommand extends Command {

    @Override
    public void execute() {
        Command result;
        Island island = null;
        try {
            int id = (Integer)parameters.get("id");
            Entity entity = island.getEntity(id);
            result = Command.create(Command.GET_ENTITY);
            result.parameters.put("type", entity.getClass().getSimpleName().toLowerCase());
            result.parameters.put("x", entity.getPosition().getX());
            result.parameters.put("y", entity.getPosition().getY());
            //entity to json
        } catch (Exception e) {
            result = Command.create(Command.ERROR);
            result.parameters.put(ErrorCommand.MESSAGE, "Incorrect id");
        }
        session.addToWriteQueue(result);
    }
}
