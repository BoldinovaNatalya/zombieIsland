package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public class Gun extends Weapon {

    private static final int RANGE = 10;
    private static final int DAMAGE = 30;
    private static final int AMMO = 1;
    private static final int DELAY = 500;
    private static final int WEIGHT = 20;

    public Gun(Point position, Island island, Integer id) {
        super(position, island, WEIGHT, id, RANGE, DAMAGE, AMMO, DELAY);
    }

    public Gun(Island island, Integer id) {
        super(new Point(HIDDEN, HIDDEN), island, WEIGHT, id, RANGE, DAMAGE, AMMO, DELAY);
    }
}