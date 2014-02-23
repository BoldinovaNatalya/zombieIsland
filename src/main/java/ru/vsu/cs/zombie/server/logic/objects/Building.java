package ru.vsu.cs.zombie.server.logic.objects;

import java.awt.geom.Point2D;

public class Building extends Entity {

    private int width = 0;
    private int height = 0;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Building(Point2D position, int width, int height) {
        super(position);
        this.width = width;
        this.height = height;
    }

}
