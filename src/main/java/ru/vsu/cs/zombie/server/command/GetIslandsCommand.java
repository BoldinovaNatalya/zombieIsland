package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GetIslandsCommand extends Command {

    @Override
    public void execute() {
        Command result = Command.create(Command.GET_ISLANDS);
        List<Object> islands = new ArrayList<Object>();
        int i = 0;
        for (Island island : Island.getIslands()) {
            Map<String, Integer> islandInfo = new TreeMap<String, Integer>();
            islandInfo.put("island_id", i++);
            islandInfo.put("max_players", island.getPlayerCount());
            islandInfo.put("current_players", island.getSessions().size());
            islands.add(islandInfo);
        }
        result.parameters.put("islands", islands);
        session.addToWriteQueue(result);
    }
}
