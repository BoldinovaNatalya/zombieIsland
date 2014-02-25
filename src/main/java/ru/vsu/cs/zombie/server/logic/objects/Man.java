package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Point;

import java.util.ArrayList;
import java.util.List;

public class Man extends Character {
    private final static int MAX_HUNGER = 100;
    private final static int MAX_THIRST = 100;

    private int playerID;
    private int hunger = 0;
    private int thirst = 0;
    private Backpack backpack = new Backpack();

    public Man(Point position, Weapon weapon, int playerID) {
        super(position, weapon);
        this.playerID = playerID;
    }

    class Backpack {
        private final static int MAX_WEIGHT = 50;
        private List<Resource> items = new ArrayList<Resource>();

        public void Put(Resource item) {

        }

        public void Extract(Resource item) {

        }


        public List<Resource> getItems() {
            return items;
        }

        public void setItems(List<Resource> items) {
            this.items = items;
        }
    }


}
