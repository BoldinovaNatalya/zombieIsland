package ru.vsu.cs.zombie.server.logic;

import ru.vsu.cs.zombie.server.logic.objects.*;
import ru.vsu.cs.zombie.server.net.Session;
import ru.vsu.cs.zombie.server.utils.Gauss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Island {

    static private List<Island> islands = new ArrayList<Island>();

    public static Island CreateIsland(int playerCount) {
        Island island = new Island(playerCount);
        islands.add(island);
        return island;
    }

    public final static int HEIGHT = 200;
    public final static int WIDTH = 200;

    private List<Building> buildings;
    private List<Base> bases;
    private List<Man> men;
    private List<Zombie> zombies;
    private List<Food> food;
    private List<Water> water;
    private List<Medicines> medicines;
    private List<Weapon> weapons;
    private List<Ammunition> ammunition;

    private int playerCount = 0;

    public int getPlayerCount() {
        return playerCount;
    }

    private List<Session> sessions = new ArrayList<Session>();

    public void addSession(Session session) {
        if (session != null) {
            sessions.add(session);
        }
    }

    public List<Session> getSessions() {
        return Collections.unmodifiableList(sessions);
    }

    private Island(int playerCount) {
        EntitySpawner spawner = new EntitySpawner(playerCount);
        spawner.spawn();
    }

    public List<Entity> getAllEntities(int id) {
        return null;
    }

    class EntitySpawner {

        private int playerCount = 0;
        private int currentID = 0;
        private Gauss gauss = new Gauss();

        private static final int CHARACTERS_COUNT = 5;
        private static final int BUILDINGS_COUNT = 10;
        private static final int ZOMBIE_COUNT = 25;

        private static final int CENTER_X = WIDTH / 2;
        private static final int CENTER_Y = HEIGHT / 2;
        private static final int DEV = 10;

        private Point getGaussPoint() {
            int x = (int)gauss.Next(CENTER_X, DEV);
            int y = (int)gauss.Next(CENTER_Y, DEV);
            return new Point(x, y);
        }

        private EntitySpawner(int playerCount) {
            this.playerCount = playerCount;
        }

        private void spawnMenAndBases() {
            spawnBases();
            men = new ArrayList<Man>();
            for (int i = 0; i < playerCount; i++) {
                for (int j = 0; j < CHARACTERS_COUNT; j++) {
                    men.add(new Man(bases.get(i).getPosition(), currentID++, null, i));
                }
            }
        }

        private void spawnBuildings() {
            buildings = new ArrayList<Building>();
            for (int i = 0; i < BUILDINGS_COUNT; i++) {
                buildings.add(new Building(getGaussPoint(), currentID++));
            }
        }

        private void spawnBases() {
            bases = new ArrayList<Base>();
            final float factor = 0.1f;
            for (int i = 0; i < playerCount; i++) {
                float x = i % 2 == 0 ? Island.WIDTH * (1 - factor) : Island.WIDTH * factor;
                float y = i / 2 == 0 ? Island.HEIGHT * (1 - factor) : Island.HEIGHT * factor;
                bases.add(new Base(new Point((int) x, (int) y), currentID++, i));
            }
        }

        private void spawnZombies() {
            zombies = new ArrayList<Zombie>();
            for (int i = 0; i < ZOMBIE_COUNT; i++) {
                zombies.add(new Zombie(getGaussPoint(), currentID++, null));
            }
        }

        private void spawnFood() {
            food = new ArrayList<Food>();
        }


        private void spawnWater() {
            water = new ArrayList<Water>();
        }

        private void spawnMedicines() {
            medicines = new ArrayList<Medicines>();
        }


        private void spawnWeapons() {
            weapons = new ArrayList<Weapon>();
        }


        private void spawnAmmunition() {
            ammunition = new ArrayList<Ammunition>();
        }

        public void spawn() {
            spawnBuildings();
            spawnMenAndBases();
            spawnZombies();
            spawnFood();
            spawnWater();
            spawnMedicines();
            spawnWeapons();
            spawnAmmunition();
        }
    }
}
