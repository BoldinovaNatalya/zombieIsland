package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public class Zombie extends  Character {

    public Zombie(Point position, Island island, Weapon weapon) {
        super(position, island, weapon);
    }
}
