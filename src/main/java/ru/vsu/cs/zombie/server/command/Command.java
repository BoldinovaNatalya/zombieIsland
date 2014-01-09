package ru.vsu.cs.zombie.server.command;

import java.util.Map;
import java.util.TreeMap;

public class Command {


    static final int ERROR = -1;
    static final int HELLO = 0;
    static final int LOGIN = 1;
    static final int REGISTRATION = 2;

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

    private Command() {

    }

    @Override
    public String toString() {
        return String.format("Command: id = %d; parameters=%s", id, parameters.toString());
    }
}
