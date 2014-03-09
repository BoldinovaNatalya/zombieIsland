package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Point;

public class Zombie extends  Character {

    public Zombie(Point position, int id, Weapon weapon) {
        super(position, id, weapon);
    }
}
