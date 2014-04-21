package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public class Teeth extends Weapon {

    private static final int RANGE = 0;
    private static final int DAMAGE = 50;
    private static final int AMMO = 0;
    private static final int DELAY = 500;

    public Teeth(Point position, Island island, int weight, Integer id) {
        super(position, island, weight, id, RANGE, DAMAGE, AMMO, DELAY);
    }

    public Teeth() {
        this(new Point(HIDDEN, HIDDEN), null, 0, 0);
    }
}
