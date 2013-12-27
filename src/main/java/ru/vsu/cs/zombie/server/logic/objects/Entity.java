package ru.vsu.cs.zombie.server.logic.objects;

import java.awt.geom.Point2D;

public class Entity {
    protected Point2D position;

    public Point2D getPosition() {
        return position;
    }

    public Entity(Point2D position) {
        this.position = position;
    }
}
