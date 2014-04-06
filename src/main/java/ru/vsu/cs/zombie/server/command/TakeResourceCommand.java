package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.objects.Man;
import ru.vsu.cs.zombie.server.logic.objects.Resource;

public class TakeResourceCommand extends Command {

    @Override
    public void execute() {
        int manID = (Integer)parameters.get("man_id");
        int resourceID = (Integer)parameters.get("id");
        Island island = session.getIsland();
        Command response;
        Resource resource = (Resource)island.getEntity(resourceID);
        if (island.playerHasCharacter(manID, session) && resource != null) {
            Man man = (Man)island.getEntity(manID);
            if (man.pickUp(resource)) {
                response = createResponse();
            } else {
                response = new ErrorCommand("Backpack is full", id);
            }
        } else {
            response = new ErrorCommand("Wrong id", id);
        }
        session.addToWriteQueue(response);
    }
}
