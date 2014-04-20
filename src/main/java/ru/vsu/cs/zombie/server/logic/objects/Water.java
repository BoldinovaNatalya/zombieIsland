package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public class Water extends Resource {

    private static final int WEIGHT = 5;

    public Water(Point position, Island island, int weight, int id) {
        super(position, island, WEIGHT, id);
    }
}
