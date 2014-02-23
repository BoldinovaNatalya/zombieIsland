package ru.vsu.cs.zombie.server.logic;

import ru.vsu.cs.zombie.server.logic.objects.*;

import java.util.List;

public class Island {

    private int playerCount = 0;

    private List<Building> buildings;
    private List<Base> bases;
    private List<Man> men;
    private List<Zombie> zombies;
    private List<Food> food;
    private List<Water> water;
    private List<Medicines> medicines;
    private List<Weapon> weapons;
    private List<Ammunition> ammunition;

    public Island(int playerCount) {
        this.playerCount = playerCount;
        ObjectSpawner objectSpawner = new ObjectSpawner(playerCount);
        buildings = objectSpawner.spawnBuildings();
        bases = objectSpawner.spawnBases();
        men = objectSpawner.spawnMen();
        zombies = objectSpawner.spawnZombies();
        food = objectSpawner.spawnFood();
        water = objectSpawner.spawnWater();
        medicines = objectSpawner.spawnMedicines();
        weapons = objectSpawner.spawnWeapons();
        ammunition = objectSpawner.spawnAmmunition();
    }

    public List<Entity> getAllEntities(int id)
    {
        return null;
    }
}
