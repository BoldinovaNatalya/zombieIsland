package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public class Medicines extends Resource {

    public Medicines(Point position, Island island, int weight) {
        super(position, island, weight);
    }
}
