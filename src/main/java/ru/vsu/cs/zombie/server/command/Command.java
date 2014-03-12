package ru.vsu.cs.zombie.server.command;

import java.util.Map;
import java.util.TreeMap;

public class Command {
    static final int ERROR = -1;

    static final int HELLO = 0;
    static final int LOGIN = 1;
    static final int REGISTER = 2;
    static final int GET_ISLANDS = 3;
    static final int CREATE_ISLAND = 4;
    static final int JOIN_ISLAND = 5;

    private int id;
    private Map<String, Object> parameters = new TreeMap<String, Object>();

    public int getId() {
        return id;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    Command(int id) {
        this.id = id;
    }

    //empty constructor for Jackson
    private Command() {

    }

    @Override
    public String toString() {
        return String.format("Command: id = %d; parameters=%s", id, parameters.toString());
    }

    void putParameter(String key, Object value) {
        parameters.put(key, value);
    }

    Object getParameter(String key) {
        return parameters.get(key);
    }
}
