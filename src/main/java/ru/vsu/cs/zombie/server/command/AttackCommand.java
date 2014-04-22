package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.objects.Character;
import ru.vsu.cs.zombie.server.logic.objects.Man;

public class AttackCommand extends Command {

    @Override
    public void execute() {
        int manID = (Integer)parameters.get("man_id");
        int targetID = (Integer)parameters.get("id");
        Command response;
        if (session.getIsland() != null && session.getIsland().playerHasCharacter(manID, session)) {
            Man man = (Man)session.getIsland().getEntity(manID);
            Character target = (Character)session.getIsland().getEntity(targetID);
            if (target != null) {
                int damage = man.attack(target);
                response = createResponse();
                response.parameters.put("damage", damage);
            } else {
                response = new ErrorCommand("Wrong target id", id);
            }
        } else {
            response = new ErrorCommand("Wrong man id", id);
        }
        session.write(response);
    }
}
