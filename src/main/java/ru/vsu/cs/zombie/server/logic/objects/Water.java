package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public class Water extends Resource {

    private static final int WEIGHT = 5;

    public Water(Point position, Island island, Integer id) {
        super(position, island, WEIGHT, id);
    }

    @Override
    public void use(Man man) {
        super.use(man);
        man.changeThirst(-Man.MAX_THIRST / 4);
    }
}
