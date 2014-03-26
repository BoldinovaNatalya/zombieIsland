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
    private Map<Integer, List<Integer>> menID = new HashMap<Integer, List<Integer>>();

    private int playerCount = 0;

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

    private Island(int playerCount) {
        this.playerCount = (playerCount>0) && (playerCount<=MAX_PLAYERS) ?
                playerCount : MAX_PLAYERS;
        EntitySpawner spawner = new EntitySpawner();
        spawner.spawn();
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
        int player_id = sessions.indexOf(session);
        return player_id != -1 ? menID.get(player_id) : null;
    }

    public boolean playerHaveCharacter(int id, Session session) {
        int player_id = sessions.indexOf(session);
        return player_id != -1 && menID.get(player_id).contains(id);
    }

    public Entity getEntity(int id) {
        return entities.get(id);
    }

    public Set<Integer> getEntitiesID() {
        return entities.keySet();
    }

    class EntitySpawner {
        private int currentID = 0;
        private Gauss gauss = new Gauss();

        private static final int CHARACTERS_COUNT = 5;
        private static final int BUILDINGS_COUNT = 10;
        private static final int ZOMBIE_COUNT = 25;

        private static final int CENTER_X = WIDTH / 2;
        private static final int CENTER_Y = HEIGHT / 2;
        private static final int DEV = 10;

        private Point getGaussPoint() {
            int y = (int)gauss.Next(CENTER_Y, DEV);
            int x = (int)gauss.Next(CENTER_X, DEV);
            return new Point(x, y);
        }

        private EntitySpawner() {
        }

        private List<Base> bases = new ArrayList<Base>();

        private void spawnMenAndBases() {
            spawnBases();
            for (int i = 0; i < playerCount; i++) {
                menID.put(i, new ArrayList<Integer>());
                for (int j = 0; j < CHARACTERS_COUNT; j++) {
                    entities.put(currentID, new Man(bases.get(i).getPosition(), Island.this, null, i));
                    menID.get(i).add(currentID);
                    currentID++;
                }
            }
        }

        private void spawnBuildings() {
            for (int i = 0; i < BUILDINGS_COUNT; i++) {
                entities.put(currentID++, new Building(getGaussPoint(), Island.this));
            }
        }

        private void spawnBases() {
            bases = new ArrayList<Base>();
            final float factor = 0.1f;
            for (int i = 0; i < playerCount; i++) {
                float x = i % 2 == 0 ? Island.WIDTH * (1 - factor) : Island.WIDTH * factor;
                float y = i / 2 == 0 ? Island.HEIGHT * (1 - factor) : Island.HEIGHT * factor;
                Base base = new Base(new Point((int) x, (int) y), Island.this, i);
                bases.add(base);
                entities.put(currentID++, base);
            }
        }

        private void spawnZombies() {
            for (int i = 0; i < ZOMBIE_COUNT; i++) {
                entities.put(currentID++, new Zombie(getGaussPoint(), Island.this, null));
            }
        }

        private void spawnFood() {
            //food = new ArrayList<Food>();
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
