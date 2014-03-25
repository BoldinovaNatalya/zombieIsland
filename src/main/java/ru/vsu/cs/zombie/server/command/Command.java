package ru.vsu.cs.zombie.server.command;

import org.codehaus.jackson.annotate.JsonProperty;
import ru.vsu.cs.zombie.server.net.Session;

import java.util.Map;
import java.util.TreeMap;

public abstract class Command {
    
    static final String ERROR = "error";
    static final String HELLO = "hello";
    static final String LOGIN = "login";
    static final String REGISTER = "register";
    static final String GET_ISLANDS = "get_islands";
    static final String CREATE_ISLAND = "create_island";
    static final String JOIN_ISLAND = "join_island";
    static final String START_GAME = "start_game";
    static final String FINISH_GAME = "finish_game";
    static final String GET_MEN = "get_men";
    static final String GET_ENTITY = "get_entity";
    static final String GET_VISIBLE_ENTITIES = "get_visible_entities";

    private static Map<String, Class> commandTypes = new TreeMap<String, Class>() {{
        put(ERROR, ErrorCommand.class);
        put(HELLO, HelloCommand.class);
        put(LOGIN, LoginCommand.class);
        put(REGISTER, RegisterCommand.class);
        put(GET_ISLANDS, GetIslandsCommand.class);
        put(CREATE_ISLAND, CreateIslandCommand.class);
        put(JOIN_ISLAND, JoinIslandCommand.class);
        put(START_GAME, StartGameCommand.class);
        put(FINISH_GAME, FinishGame.class);
        put(GET_MEN, GetMenCommand.class);
        put(GET_ENTITY, GetEntityCommand.class);
        put(GET_VISIBLE_ENTITIES, GetVisibleCommand.class);
    }};

    public static Command create(String name) {
        Class c = commandTypes.get(name);
        if (c != null) {
            try {
                Command command = (Command)c.newInstance();
                command.name = name;
                return command;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Class getClassByName(String name) {
        return commandTypes.get(name);
    }

    @JsonProperty("name")
    protected String name;

    @JsonProperty("parameters")
    protected Map<String, Object> parameters = new TreeMap<String, Object>();

    protected Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    protected Command() {

    }

    @Override
    public String toString() {
        return String.format("%s: name = %s; parameters=%s", this.getClass().getSimpleName(), name, parameters.toString());
    }

    public abstract void execute();
}
