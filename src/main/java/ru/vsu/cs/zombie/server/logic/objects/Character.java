package ru.vsu.cs.zombie.server.logic.objects;


import java.awt.geom.Point2D;
import java.util.List;

public class Character extends  Entity {
    private final static int MAX_HEALTH = 100;
    private final static int MAX_STAMINA = 100;
    private final static int MAX_ACCURANCY = 100;
    public final static int DEFAULT_VISIBILITY = 10;

 
    protected int id;
    protected int health = MAX_HEALTH;
    protected int speed;
    protected Point2D destination = position ;
    protected  int stamina = MAX_STAMINA;
    protected  int accurancy = MAX_ACCURANCY;
    protected  Weapon weapon;
    protected List<Entity>  visibleEntities = null;

    public Character(Point2D position, Weapon weapon) {
        super(position);
        this.weapon = weapon;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Point2D getDestination() {
        return destination;
    }

    public void setDestination(Point2D destination) {
        this.destination = destination;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getAccurancy() {
        return accurancy;
    }

    public void setAccurancy(int accurancy) {
        this.accurancy = accurancy;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public List<Entity> getVisibleEntities() {
        return visibleEntities;
    }

    public void setVisibleEntities(List<Entity> visibleEntities) {
        this.visibleEntities = visibleEntities;
    }
}
