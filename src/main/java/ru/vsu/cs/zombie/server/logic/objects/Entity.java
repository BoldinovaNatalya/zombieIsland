package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public class Entity {

    protected Point position;
    protected Island island;

    public Point getPosition() {
        return position;
    }

    public Entity(Point position, Island island) {
        this.position = position;
        this.island = island;
    }
}
