package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public class Zombie extends  Character {

    public Zombie(Point position, Island island, Integer id) {
        super(position, island, new Teeth(), id);
    }

    public void action() {
        if (!isAlive()) {
            return;
        }

        double minDistance = DEFAULT_VISIBILITY;
        Man target = null;
        for (Man man : island.getMen()) {
            double distance = position.distance(man.position);
            if (distance < minDistance) {
                target = man;
                minDistance = distance;
            }
        }

        if (target != null) {
            if (minDistance == 0) {
                attack(target);
            } else {
                move(target.position);
            }
        } else {
            int x = (int) Math.round(Math.random() * Island.WIDTH);
            int y = (int) Math.round(Math.random() * Island.HEIGHT);
            move(x, y);
        }
    }

    @Override
    public void changeState() {
        super.changeState();
    }
}
