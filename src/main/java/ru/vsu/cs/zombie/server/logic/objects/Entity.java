package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Point;

public class Entity {
    protected Point position;

    public Point getPosition() {
        return position;
    }

    public Entity(Point position) {
        this.position = position;
    }
}
