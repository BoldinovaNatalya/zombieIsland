package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.objects.Man;
import ru.vsu.cs.zombie.server.logic.objects.Resource;

public class DropResourceCommand extends Command {


    @Override
    public void execute() {
        int manID = (Integer) parameters.get("man_id");
        int resourceID = (Integer) parameters.get("id");
        Island island = session.getIsland();
        Command response;
        Resource resource = (Resource) island.getEntity(resourceID);
        Man man = (Man) island.getEntity(manID);
        if (island.playerHasCharacter(manID, session) && resource != null && man.drop(resource)) {
            response = createResponse();
        } else {
            response = new ErrorCommand("Wrong id", id);
        }
        session.addToWriteQueue(response);
    }
}

