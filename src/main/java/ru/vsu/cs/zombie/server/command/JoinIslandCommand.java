package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;

public class JoinIslandCommand extends Command {

    @Override
    public void Execute() {
        int id = (Integer)parameters.get("island_id");
        Island island = Island.getIsland(id);
        island.addSession(session);
        session.addToWriteQueue(Command.Create(Command.JOIN_ISLAND));
    }
}
