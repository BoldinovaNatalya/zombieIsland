package ru.vsu.cs.zombie.server.logic.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

import java.util.Date;

public class Character extends Entity {
    public final static int MAX_HEALTH = 100;
    public final static int MAX_STAMINA = 100;
    public final static int MAX_ACCURACY = 100;
    public final static int DEFAULT_VISIBILITY = 10;
    public final static int DEFAULT_SPEED = 5;
    public final static int MOVEMENT_TIME = 1000; //milliseconds
    public final static int MISS = -1;

    protected static int changeValue(int value, int offset, int maxValue) {
        value = value + offset;
        value = value < 0 ? 0 : value > maxValue ? maxValue : value;
        return value;
    }

    @JsonProperty("health")
    protected int health = MAX_HEALTH;

    @JsonProperty("stamina")
    protected int stamina = MAX_STAMINA;

    @JsonProperty("weapon")
    protected Weapon weapon;

    public Character(Point position, Island island, Weapon weapon) {
        super(position, island);
        this.weapon = weapon;
    }

    void changeHealth(int offset) {
        this.health = changeValue(health, offset, MAX_HEALTH);
    }

    void changeStamina(int offset) {
        this.stamina = changeValue(stamina, offset, MAX_STAMINA);
    }

    public void changeState() {

    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getSpeed() {
        return DEFAULT_SPEED;
    }

    public int getAccuracy() {
        return MAX_ACCURACY;
    }

    private Date lastMoveTime = new Date();
    private Date lastAttackTime = new Date();

    public int attack(Character target) {
        if (!isAlive() || weapon == null) {
            return MISS;
        }
        Date now = new Date();
        if (now.getTime() - lastAttackTime.getTime() < weapon.getDelay()) {
            return MISS;
        }
        if (position.distance(target.getPosition()) > weapon.getRange()) {
            return MISS;
        }
        //todo: addo ammo
        lastAttackTime = now;
        target.changeHealth(-weapon.getDamage());
        return weapon.getDamage();
    }

    public void move(Integer x, Integer y) {
        if (!isAlive()) {
            return;
        }
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
