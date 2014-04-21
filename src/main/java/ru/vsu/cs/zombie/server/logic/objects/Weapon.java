package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public abstract class Weapon extends Resource {

    protected int damage;
    protected int range;
    protected int ammoPerAttack;
    protected int delay;

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public int getAmmoPerAttack() {
        return ammoPerAttack;
    }

    public int getDelay() {
        return delay;
    }

    public Weapon(Point position, Island island, int weight, Integer id, int range, int damage, int ammoPerAttack, int delay) {
        super(position, island, weight, id);
        this.damage = damage;
        this.range = range;
        this.ammoPerAttack = ammoPerAttack;
        this.delay = delay;
    }
}
