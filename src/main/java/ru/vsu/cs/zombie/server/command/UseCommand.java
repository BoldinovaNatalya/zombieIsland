package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.objects.Man;
import ru.vsu.cs.zombie.server.logic.objects.Resource;

public class UseCommand extends Command {

    @Override
    public void execute() {
        int manID = (Integer)parameters.get("man_id");
        int resourceID = (Integer)parameters.get("id");
        Command response;
        if (session.getIsland().playerHasCharacter(manID, session)) {
            Resource resource = (Resource)session.getIsland().getEntity(resourceID);
            Man man = (Man)session.getIsland().getEntity(manID);
            if (resource != null && man.has(resource)) {
                man.use(resource);
                response = createResponse();
            } else {
                response = new ErrorCommand("Wrong resource id", id);
            }
        } else {
            response = new ErrorCommand("Wrong man id", id);
        }
        session.write(response);
    }
}
