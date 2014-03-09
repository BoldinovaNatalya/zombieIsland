package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Point;

public class Building extends Entity {

    public final static int HEIGHT = 2;
    public final static int WIDTH = 2;

    public Building(Point position, int id) {
        super(position, id);
    }

}
