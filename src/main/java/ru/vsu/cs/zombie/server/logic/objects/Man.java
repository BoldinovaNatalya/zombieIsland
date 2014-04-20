package ru.vsu.cs.zombie.server.logic.objects;

import org.codehaus.jackson.annotate.JsonProperty;
import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Man extends Character {
    private final static int MAX_HUNGER = 100;
    private final static int MAX_THIRST = 100;

    private int hunger = 0;
    private int thirst = 0;

    @JsonProperty("backpack")
    private Backpack backpack = new Backpack();

    public Man(Point position, Island island, Weapon weapon) {
        super(position, island, weapon);
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

    public void use(Resource item) {

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

        Set<Resource> getItems() {
            return Collections.unmodifiableSet(items);
        }
    }


}
