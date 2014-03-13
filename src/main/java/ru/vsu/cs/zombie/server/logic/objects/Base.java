package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public class Base extends Building {

    private int playerID = -1;

    public Base(Point position, Island island, int playerID) {
        super(position, island);
        this.playerID = playerID;
    }
}
