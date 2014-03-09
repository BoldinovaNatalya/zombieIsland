package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Point;

public class Base extends Building {

    private int playerID = -1;

    public Base(Point position, int id, int playerID) {
        super(position, id);
        this.playerID = playerID;
    }
}
