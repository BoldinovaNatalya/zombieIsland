package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;

import java.util.List;

public class GetMenCommand extends Command {

    @Override
    public void execute() {
        Island island = session.getIsland();
        Command result;
        List<Integer> men = null;
        if (island != null) {
            men = island.getMenID(session);
        }
        if (men != null) {
            result = Command.create(Command.GET_MEN);
            result.parameters.put("men", men);
        } else {
            result = Command.create(Command.ERROR);
            result.parameters.put("message", "Error");
        }
        session.addToWriteQueue(result);
    }
}
