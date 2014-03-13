package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public class Food extends Resource {

    public Food(Point position, Island island, int weight) {
        super(position, island, weight);
    }
}
