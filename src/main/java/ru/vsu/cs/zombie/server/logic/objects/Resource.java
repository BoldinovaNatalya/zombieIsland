package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Point;

public class Resource extends Entity {
    protected final int weight;
    private Man owner;


    public Resource(Point position, int weight) {
        super(position);
        this.weight = weight;

    }

    public Man getOwner() {
        return owner;
    }

    public void setOwner(Man owner) {
        this.owner = owner;
    }

    public int getWeight() {
        return weight;
    }

}
