package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

import java.util.Date;

public class Character extends Entity {
    private final static int MAX_HEALTH = 100;
    private final static int MAX_STAMINA = 100;
    private final static int MAX_ACCURACY = 100;
    public final static int DEFAULT_VISIBILITY = 10;
    public final static int DEFAULT_SPEED = 50;
    public final static int MOVEMENT_TIME = 1000; //milliseconds

    protected int health = MAX_HEALTH;
    protected int stamina = MAX_STAMINA;
    protected Weapon weapon;

    public Character(Point position, Island island, Weapon weapon) {
        super(position, island);
        this.weapon = weapon;
    }

    public int getHealth() {
        return health;
    }

    void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return DEFAULT_SPEED;
    }

    public int getStamina() {
        return stamina;
    }

    public int getAccuracy() {
        return MAX_ACCURACY;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    private Date lastMoveTime = new Date();

    public void move(Integer x, Integer y) {
        Date now = new Date();
        double hypotenuse = position.distance(x, y);
        long delay = now.getTime() - lastMoveTime.getTime();
        double time = delay >= MOVEMENT_TIME ? MOVEMENT_TIME : delay % MOVEMENT_TIME;
        double distance = getSpeed() * time / MOVEMENT_TIME;
        double k = (distance > hypotenuse ? hypotenuse : distance) / hypotenuse;
        int offsetX  = (int)((x - position.getX()) * k);
        int offsetY = (int)((y - position.getY()) * k);
        int newX = getPosition().getX() + offsetX;
        int newY = getPosition().getY() + offsetY;
        newX = newX > Island.WIDTH ? Island.WIDTH : newX < 0 ? 0 : newX;
        newY = newY > Island.HEIGHT ? Island.HEIGHT : newY < 0 ? 0 : newY;
        position = new Point(newX, newY);
        lastMoveTime = now;
    }
}
