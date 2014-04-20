package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public class Food extends Resource {

    private static final int WEIGHT = 10;

    public Food(Point position, Island island, Integer id) {
        super(position, island, WEIGHT, id);
    }
}
