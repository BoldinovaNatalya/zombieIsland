package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Point;

public class Entity {

    protected Point position;
    protected int id;

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public Entity(Point position, int id) {
        this.position = position;
        this.id = id;
    }
}
