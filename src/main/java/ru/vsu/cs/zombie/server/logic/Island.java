package ru.vsu.cs.zombie.server.logic;

import ru.vsu.cs.zombie.server.logic.objects.*;
import ru.vsu.cs.zombie.server.logic.objects.Character;
import ru.vsu.cs.zombie.server.net.Session;
import ru.vsu.cs.zombie.server.utils.Gauss;

import java.util.*;

public class Island {

    private static final int MAX_PLAYERS = 4;
    private static List<Island> islands = new ArrayList<Island>();

    public static Island CreateIsland(int playerCount) {
        Island island = new Island(playerCount);
        islands.add(island);
        return island;
    }

    public static Island getIsland(int id) {
        return islands.get(id);
    }

    public static List<Island> getIslands() {
        return Collections.unmodifiableList(islands);
    }

    public final static int HEIGHT = 200;
    public final static int WIDTH = 200;

    private List<Session> sessions = new ArrayList<Session>();
    private Map<Integer, Entity> entities = new HashMap<Integer, Entity>();
    private Map<Session, List<Integer>> menID = new HashMap<Session, List<Integer>>();
    private Map<Session, Base> bases = new HashMap<Session, Base>();
    private Map<Point, Building> buildings = new HashMap<Point, Building>();
    private int playerCount = 0;

    private Island(int playerCount) {
        this.playerCount = (playerCount>0) && (playerCount<=MAX_PLAYERS) ?
                playerCount : MAX_PLAYERS;
    }

    public void start() {
        new EntitySpawner().spawn();
        islands.remove(this);
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void addSession(Session session) {
        if (session != null) {
            sessions.add(session);
            session.setIsland(this);
        }
    }

    public List<Session> getSessions() {
        return Collections.unmodifiableList(sessions);
    }

    public List<Integer> getVisibleEntities(int id) {
        Character character = (Character)getEntity(id);
        assert character != null;
        List<Integer> visibleEntities = new ArrayList<Integer>();
        for (Integer i : entities.keySet()) {
            if (entities.get(i).getPosition().distance(character.getPosition()) < Man.DEFAULT_VISIBILITY) {
                visibleEntities.add(i);
            }
        }
        return visibleEntities;
    }

    public List<Integer> getMenID(Session session) {
        return menID.get(session);
    }

    public Point getBase(Session session) {
        return bases.get(session).getPosition();
    }

    public boolean playerHasCharacter(int id, Session session) {
        return menID.get(session).contains(id);
    }

    public Entity getEntity(int id) {
        return entities.get(id);
    }

    public Set<Integer> getEntitiesID() {
        return entities.keySet();
    }

    public Building getBuilding(Point point) {
        return buildings.get(point);
    }

    class EntitySpawner {
        private int currentID = 0;
        private Gauss gauss = new Gauss();

        private static final int CHARACTERS_COUNT = 5;
        private static final int BUILDINGS_COUNT = 10;
        private static final int ZOMBIE_COUNT = 25;
        private static final int FOOD_COUNT = 50;

        private static final int CENTER_X = WIDTH / 2;
        private static final int CENTER_Y = HEIGHT / 2;
        private static final int DEV = 20;

        private Point getGaussPoint() {
            int y = (int)gauss.Next(CENTER_Y, DEV);
            int x = (int)gauss.Next(CENTER_X, DEV);
            return new Point(x, y);
        }

        private EntitySpawner() {
        }

        private void spawnMenAndBases() {
            spawnBases();
            for (int i = 0; i < playerCount; i++) {
                menID.put(sessions.get(i), new ArrayList<Integer>());
                for (int j = 0; j < CHARACTERS_COUNT; j++) {
                    entities.put(currentID, new Man(bases.get(sessions.get(i)).getPosition(), Island.this, null));
                    menID.get(sessions.get(i)).add(currentID);
                    currentID++;
                }
            }
        }

        private void spawnBuildings() {
            for (int i = 0; i < BUILDINGS_COUNT; i++) {
                Building building = new Building(getGaussPoint(), Island.this);
                entities.put(currentID++,building);
                for (int j = 0; j < Building.WIDTH; j++) {
                    for (int k = 0; k < Building.HEIGHT; k++) {
                        buildings.put(new Point(building.getPosition().getX() + j,
                                building.getPosition().getY() + k), building);
                    }
                }
            }
        }

        private void spawnBases() {
            final float factor = 0.1f;
            for (int i = 0; i < playerCount; i++) {
                int x = (int)(i % 2 == 0 ? Island.WIDTH * (1 - factor) : Island.WIDTH * factor);
                int y = (int)(i / 2 == 0 ? Island.HEIGHT * (1 - factor) : Island.HEIGHT * factor);
                Base base = new Base(new Point((int) x, (int) y), Island.this);
                bases.put(sessions.get(i), base);
                for (int j = 0; j < Building.WIDTH; j++) {
                    for (int k = 0; k < Building.HEIGHT; k++) {
                        buildings.put(new Point(x + j, y + k), base);
                    }
                }
                entities.put(currentID++, base);
            }
        }

        private void spawnZombies() {
            for (int i = 0; i < ZOMBIE_COUNT; i++) {
                entities.put(currentID++, new Zombie(getGaussPoint(), Island.this, null));
            }
        }

        private void spawnFood() {
            for (int i = 0; i < FOOD_COUNT; i++) {
                entities.put(currentID++, new Food(getGaussPoint(), Island.this));
            }
        }

        private void spawnWater() {
            //water = new ArrayList<Water>();
        }

        private void spawnMedicines() {
            //medicines = new ArrayList<Medicines>();
        }


        private void spawnWeapons() {
            //weapons = new ArrayList<Weapon>();
        }


        private void spawnAmmunition() {
            //ammunition = new ArrayList<Ammunition>();
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
