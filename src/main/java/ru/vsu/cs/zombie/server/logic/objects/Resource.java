package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public class Resource extends Entity {
    protected final int weight;
    private boolean isPickedUp = false;

    public Resource(Point position, Island island, int weight) {
        super(position, island);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void drop(Point position) {
        isPickedUp = false;
        this.position = position;
    }

    public void pickUp() {
        isPickedUp = true;
        position = new Point(-1, -1);
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }
}
