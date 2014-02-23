package ru.vsu.cs.zombie.server.logic.objects;

import java.awt.geom.Point2D;

public class Base extends Building {

    private int playerID = -1;

    public Base(Point2D position, int width, int height, int playerID) {
        super(position, width, height);
        this.playerID = playerID;
    }
}
