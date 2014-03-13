package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.logic.Island;

public class CreateIslandCommand extends Command {

    private static String PLAYERS = "players";

    @Override
    public void execute() {
        int players = (Integer)parameters.get(PLAYERS);
        Island island = Island.CreateIsland(players);
        island.addSession(session);
        Command result = Command.Create(Command.CREATE_ISLAND);
        session.addToWriteQueue(result);
    }
}
