package ru.vsu.cs.zombie.server.logic;

import ru.vsu.cs.zombie.server.logic.objects.*;

import java.util.ArrayList;
import java.util.List;

class ObjectSpawner {

    private int playerCount = 0;

    ObjectSpawner(int playerCount) {
        this.playerCount = playerCount;
    }

    List<Man> spawnMen() {
        List<Man> men = new ArrayList<Man>();
        return men;
    }

    List<Building> spawnBuildings() {
        List<Building> buildings = new ArrayList<Building>();
        return buildings;
    }

    List<Base> spawnBases() {
        List<Base> bases = new ArrayList<Base>();
        final float factor = 0.1f;
        for (int i = 0; i < playerCount; i++) {
            float offsetX = i % 2 == 0 ? Island.WIDTH * (1 - factor) : Island.WIDTH * factor;
            float offsetY = i / 2 == 0 ? Island.HEIGHT * (1 - factor) : Island.HEIGHT * factor;
        }
        return bases;
    }

    List<Zombie> spawnZombies() {
        List<Zombie> zombies = new ArrayList<Zombie>();
        return zombies;
    }

    List<Food> spawnFood() {
        List<Food> food = new ArrayList<Food>();
        return food;
    }


    List<Water> spawnWater() {
        List<Water> water = new ArrayList<Water>();
        return water;
    }

    List<Medicines> spawnMedicines() {
        List<Medicines> medicines = new ArrayList<Medicines>();
        return medicines;
    }


    List<Weapon> spawnWeapons() {
        List<Weapon> weapons = new ArrayList<Weapon>();
        return weapons;
    }


    List<Ammunition> spawnAmmunition() {
        List<Ammunition> ammunition = new ArrayList<Ammunition>();
        return ammunition;
    }
}
