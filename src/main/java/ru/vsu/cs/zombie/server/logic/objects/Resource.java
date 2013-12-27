package ru.vsu.cs.zombie.server.logic.objects;


import java.awt.geom.Point2D;

public class Resource extends Entity {
    protected final int weight;
    private Man owner;


    public Resource(Point2D position, int weight) {
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
