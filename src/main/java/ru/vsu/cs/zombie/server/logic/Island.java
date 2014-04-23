package ru.vsu.cs.zombie.server.logic;

import org.apache.log4j.Logger;
import ru.vsu.cs.zombie.server.command.Command;
import ru.vsu.cs.zombie.server.logic.objects.*;
import ru.vsu.cs.zombie.server.logic.objects.Character;
import ru.vsu.cs.zombie.server.net.Session;
import ru.vsu.cs.zombie.server.utils.Gauss;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Island {

    private static Logger logger = Logger.getLogger(Island.class.getSimpleName());

    private static final int MAX_PLAYERS = 4;
    private static final int TIMER_TICK = 60 * 1000;
    private static final int ZOMBIE_TICK = 1000;

    private static List<Island> islands = new ArrayList<Island>();

    public static Island createIsland(int playerCount) {
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
    private Map<Session, Set<Integer>> menID = new HashMap<Session, Set<Integer>>();
    private Map<Session, Base> bases = new HashMap<Session, Base>();
    private Map<Point, Building> buildings = new HashMap<Point, Building>();
    private List<Man> men = new ArrayList<Man>();
    private List<Zombie> zombies = new ArrayList<Zombie>();
    private int playerCount = 0;
    private boolean isGameStarted = false;

    private Timer backGroundTimer = new Timer();
    private Timer zombieTimer = new Timer();

    private Island(int playerCount) {
        this.playerCount = (playerCount>0) && (playerCount<=MAX_PLAYERS) ?
                playerCount : MAX_PLAYERS;
    }

    public void start() {
        isGameStarted = true;
        new EntitySpawner().spawn();
        islands.remove(this);
        backGroundTimer.scheduleAtFixedRate(new BackgroundTask(), TIMER_TICK, TIMER_TICK);
        zombieTimer.scheduleAtFixedRate(new ZombieTask(), ZOMBIE_TICK, ZOMBIE_TICK);
    }

    private void finish() {
        for (Session session : sessions) {
            session.write(Command.getCommandByName(Command.FINISH_GAME));
            sessions.remove(session);
        }
    }


    private void backgroundWork() {
        for (Man man : men) {
            man.changeState();
        }
        for (Zombie zombie : zombies) {
            zombie.changeState();
        }
    }

    private void zombieWork() {
        for (Zombie zombie : zombies) {
            zombie.action();
        }
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

    public void removeSession(Session session) {
        sessions.remove(session);
        if (isGameStarted) {
            playerCount--;
            if (playerCount == 0) {
                finish();
            } else {
                for (Integer id : menID.get(session)) {
                    entities.remove(id);
                }
                menID.remove(session);
                bases.remove(session);
            }
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

    public Set<Integer> getMenID(Session session) {
        return menID.get(session);
    }

    public Base getBase(Session session) {
        return bases.get(session);
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

    public List<Man> getMen() {
        return Collections.unmodifiableList(men);
    }

    public Building getBuilding(Point point) {
        return buildings.get(point);
    }

    public void remove(Entity entity) {
        entities.remove(entity.getId());
        if (entity instanceof Zombie) {
            zombies.remove(entity);
        }
        if (entity instanceof Man) {
            int count = 0;
            men.remove(entity);
            for (Session session : menID.keySet()) {
                Set<Integer> id = menID.get(session);
                if (id.remove(entity.getId()) && id.isEmpty()) {
                    session.write(Command.getCommandByName(Command.DEFEAT));
                }
                if (id.isEmpty()) {
                    count++;
                }
            }
            if (count == playerCount - 1) {
                finish();
            }
        }
    }

    private class EntitySpawner {

        private int currentID = 0;
        private Gauss gauss = new Gauss();

        private static final int CHARACTERS_COUNT = 5;
        private static final int BUILDINGS_COUNT = 10;
        private static final int ZOMBIE_COUNT = 25;
        private static final int FOOD_COUNT = 50;
        private static final int WATER_COUNT = 50;
        private static final int MEDICINES_COUNT = 50;
        private static final int GUN_COUNT = 10;
        private static final int AMMUNITION_COUNT = 150;

        private static final int CENTER_X = WIDTH / 2;
        private static final int CENTER_Y = HEIGHT / 2;
        private static final int DEV = 20;

        private Map<Class, Integer> resourcesCount = new HashMap<Class, Integer>();

        {
            resourcesCount.put(Food.class, FOOD_COUNT);
            resourcesCount.put(Water.class, WATER_COUNT);
            resourcesCount.put(Medicines.class, MEDICINES_COUNT);
            resourcesCount.put(Gun.class, GUN_COUNT);
            resourcesCount.put(Ammunition.class, AMMUNITION_COUNT);
        }


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
                menID.put(sessions.get(i), new HashSet<Integer>());
                for (int j = 0; j < CHARACTERS_COUNT; j++) {
                    Weapon weapon = j % 2 == 0 ? null : new Gun(Island.this, currentID++);
                    Man man = new Man(bases.get(sessions.get(i)).getPosition(), Island.this, weapon, currentID, i);
                    entities.put(currentID, man);
                    men.add(man);
                    menID.get(sessions.get(i)).add(currentID);
                    currentID++;
                }
            }
        }

        private void spawnBuildings() {
            for (int i = 0; i < BUILDINGS_COUNT; i++) {
                Building building = new Building(getGaussPoint(), Island.this, currentID);
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
                Base base = new Base(new Point(x, y), Island.this, currentID);
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
                Zombie zombie = new Zombie(getGaussPoint(), Island.this, currentID);
                entities.put(currentID++, zombie);
                zombies.add(zombie);
            }
        }

        private void spawnResources() {
            for (Class c : resourcesCount.keySet()) {
                try {
                    Constructor constructor = c.getConstructor(new Class[] {Point.class, Island.class, Integer.class});
                    for (int i = 0; i < resourcesCount.get(c); i++) {
                        entities.put(currentID, (Resource)constructor.newInstance(getGaussPoint(), Island.this, currentID));
                        currentID++;
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }

        public void spawn() {
            spawnBuildings();
            spawnMenAndBases();
            spawnZombies();
            spawnResources();
        }
    }

    private class BackgroundTask extends TimerTask {
        @Override
        public void run() {
            Island.this.backgroundWork();
        }
    }

    private class ZombieTask extends TimerTask {
        @Override
        public void run() {
            Island.this.zombieWork();
        }
    }
}
