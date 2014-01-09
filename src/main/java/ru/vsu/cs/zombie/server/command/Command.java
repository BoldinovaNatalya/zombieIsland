package ru.vsu.cs.zombie.server.command;

import java.util.Map;
import java.util.TreeMap;

public class Command {
    private int id;
    private Map<String, Object> parameters = new TreeMap<String, Object>();

    public int getId() {
        return id;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return String.format("id = %d", id);
    }
}
