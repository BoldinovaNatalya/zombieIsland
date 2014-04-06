package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.objects.Entity;

public class GetEntityCommand extends Command {

    @Override
    public void execute() {
        Command result;
        Island island = session.getIsland();
        try {
            int id = (Integer)parameters.get("id");
            Entity entity = island.getEntity(id);
            result = createResponse();
            result.parameters.put("type", entity.getClass().getSimpleName().toLowerCase());
            //result.parameters.put("x", entity.getPosition().getX());
            //result.parameters.put("y", entity.getPosition().getY());
            result.parameters.put("entity", entity);
        } catch (Exception e) {
            result = new ErrorCommand("Incorrect id", id);
        }
        session.addToWriteQueue(result);
    }
}
