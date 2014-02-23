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
        return  buildings;
    }

    List<Base> spawnBases() {
        List<Base> bases = new ArrayList<Base>();
        return bases;
    }

    public List<Zombie> spawnZombies() {
        List<Zombie> zombies = new ArrayList<Zombie>();
        return zombies;
    }

    public List<Food> spawnFood() {
        List<Food> food = new ArrayList<Food>();
        return food;
    }


    public List<Water> spawnWater() {
        List<Water> water = new ArrayList<Water>();
        return water;
    }

    public List<Medicines> spawnMedicines() {
        List<Medicines> medicines = new ArrayList<Medicines>();
        return medicines;
    }


    public List<Weapon> spawnWeapons() {
        List<Weapon> weapons = new ArrayList<Weapon>();
        return weapons;
    }


    public List<Ammunition> spawnAmmunition() {
        List<Ammunition> ammunition = new ArrayList<Ammunition>();
        return ammunition;
    }
}
