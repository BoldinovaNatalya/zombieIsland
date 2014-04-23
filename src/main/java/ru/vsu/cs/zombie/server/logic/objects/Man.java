package ru.vsu.cs.zombie.server.logic.objects;


import com.fasterxml.jackson.annotation.JsonProperty;
import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Man extends Character {
    public final static int MAX_HUNGER = 100;
    public final static int MAX_THIRST = 100;

    @JsonProperty("hunger")
    private int hunger = 0;

    @JsonProperty("thirst")
    private int thirst = 0;

    @JsonProperty("team")
    private int team;

    public void changeThirst(int offset) {
        this.thirst = changeValue(thirst, offset, MAX_THIRST);
    }

    public void changeHunger(int offset) {
        this.hunger = changeValue(hunger, offset, MAX_HUNGER);
    }

    @Override
    public void changeState() {
        super.changeState();
        changeHunger(MAX_HUNGER/10);
        changeThirst(MAX_THIRST/20);
    }

    @JsonProperty("backpack")
    private Backpack backpack = new Backpack();

    public Man(Point position, Island island, Weapon weapon, Integer id, Integer team) {
        super(position, island, weapon, id);
        this.team = team;
    }

    public boolean pickUp(Resource item) {
        if  (item.position.equals(position)) {
            return backpack.put(item);
        }
        Base base = getBase();
        if  (base != null && base.getResources().contains(item)) {
            if (backpack.put(item)) {
                base.remove(item);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean drop(Resource item) {
        if (backpack.remove(item)) {
            if (getBase() != null) {
                getBase().add(item);
            }
            return true;
        }
        return false;
    }

    public boolean has(Resource resource) {
        return backpack.has(resource);
    }

    public void use(Resource item) {
        item.use(this);
        drop(item);
    }

    @Override
    void changeHealth(int offset) {
        super.changeHealth(offset);
        if (!isAlive()) {
            for (Resource item : backpack.getItems()) {
                backpack.remove(item);
            }
        }
    }

    private Base getBase() {
        return (Base)island.getBuilding(position);
    }

    class Backpack {
        private final static int MAX_WEIGHT = 50;
        @JsonProperty("weight")
        private int currentWeight = 0;
        @JsonProperty("items")
        private Set<Resource> items = new HashSet<Resource>();

        boolean put(Resource item) {
            if (currentWeight + item.getWeight() <= MAX_WEIGHT) {
                items.add(item);
                currentWeight+=item.getWeight();
                item.pickUp();
                return true;
            }
            return false;
        }

        boolean remove(Resource item) {
            if (items.remove(item)) {
                item.drop(Man.this.position);
                return true;
            } else {
                return false;
            }
        }

        boolean has(Resource item) {
            return items.contains(item);
        }

        Set<Resource> getItems() {
            return Collections.unmodifiableSet(items);
        }
    }


}
