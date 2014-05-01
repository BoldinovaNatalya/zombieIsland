package ru.vsu.cs.zombie.server.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.vsu.cs.zombie.server.net.Session;

import java.util.Map;
import java.util.TreeMap;

public abstract class Command {

    public static final String ERROR = "error";
    public static final String HELLO = "hello";
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";
    public static final String GET_ISLANDS = "get_islands";
    public static final String CREATE_ISLAND = "create_island";
    public static final String JOIN_ISLAND = "join_island";
    public static final String START_GAME = "start_game";
    public static final String FINISH_GAME = "finish_game";
    public static final String DEFEAT = "defeat";
    public static final String GET_ENTITY = "get_entity";
    public static final String GET_VISIBLE_ENTITIES = "get_visible_entities";
    public static final String MOVE = "move";
    public static final String TAKE = "take";
    public static final String DROP = "drop";
    public static final String GET_BASE = "get_base";
    public static final String USE = "use";
    public static final String ATTACK = "attack";

    static Map<String, Class> commandTypes = new TreeMap<String, Class>();

    static {
        commandTypes.put(ERROR, ErrorCommand.class);
        commandTypes.put(HELLO, HelloCommand.class);
        commandTypes.put(LOGIN, LoginCommand.class);
        commandTypes.put(REGISTER, RegisterCommand.class);
        commandTypes.put(GET_ISLANDS, GetIslandsCommand.class);
        commandTypes.put(CREATE_ISLAND, CreateIslandCommand.class);
        commandTypes.put(JOIN_ISLAND, JoinIslandCommand.class);
        commandTypes.put(START_GAME, StartGameCommand.class);
        commandTypes.put(FINISH_GAME, FinishGameCommand.class);
        commandTypes.put(DEFEAT, DefeatCommand.class);
        commandTypes.put(GET_ENTITY, GetEntityCommand.class);
        commandTypes.put(GET_VISIBLE_ENTITIES, GetVisibleCommand.class);
        commandTypes.put(MOVE, MoveCommand.class);
        commandTypes.put(TAKE, TakeResourceCommand.class);
        commandTypes.put(DROP, DropResourceCommand.class);
        commandTypes.put(GET_BASE, GetBaseCommand.class);
        commandTypes.put(USE, UseCommand.class);
        commandTypes.put(ATTACK, AttackCommand.class);
    }

    protected static Command createResponse(String name, int id, Session session) {
        Class c = commandTypes.get(name);
        if (c != null) {
            try {
                Command command = (Command) c.newInstance();
                command.name = name;
                command.id = id;
                command.session = session;
                return command;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    protected Command createResponse() {
        return createResponse(name, id, session);
    }

    public static Class getClassByName(String name) {
        return commandTypes.get(name);
    }

    public static Command getCommandByName(String name) {
        Class c = commandTypes.get(name);
        Command command = null;
        if (c != null) {
            try {
                command = (Command) c.newInstance();
                command.name = name;
                command.id = 0;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return command;
    }

    @JsonProperty("id")
    protected int id;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("parameters")
    protected Map<String, Object> parameters = new TreeMap<String, Object>();

    @JsonIgnore
    protected Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public void putParameter(String key, Object value) {
        parameters.put(key, value);
    }

    protected Command() {

    }

    @Override
    public String toString() {
        return String.format("%s: name = %s; id = %d; parameters=%s",
                this.getClass().getSimpleName(), name, id, parameters.toString());
    }

    public abstract void execute();
}
