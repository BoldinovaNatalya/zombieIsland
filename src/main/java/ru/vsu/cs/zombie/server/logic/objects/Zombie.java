package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public class Zombie extends  Character {

    public Zombie(Point position, Island island) {
        super(position, island, new Teeth());
    }

    public void action() {
        int x = (int)Math.round(Math.random() * Island.WIDTH);
        int y = (int)Math.round(Math.random() * Island.HEIGHT);
        move(x, y);
    }
}
