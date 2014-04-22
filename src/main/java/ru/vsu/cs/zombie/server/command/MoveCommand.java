package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.objects.Man;

public class MoveCommand extends Command {

    @Override
    public void execute() {
        Island island = session.getIsland();
        Command result;
        try {
            Integer id = (Integer)parameters.get("man_id");
            if (island != null && island.playerHasCharacter(id, session)) {
                Integer x = (Integer)parameters.get("x");
                Integer y = (Integer)parameters.get("y");
                Man man = (Man)island.getEntity(id);
                man.move(x, y);
                result = createResponse();
                result.parameters.put("x", man.getPosition().getX());
                result.parameters.put("y", man.getPosition().getY());
            } else {
                result = new ErrorCommand("Wrong id", id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new ErrorCommand("Json parsing error", id);
        }
        session.write(result);
    }
}
