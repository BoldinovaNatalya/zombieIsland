package ru.vsu.cs.zombie.server.logic.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public class Entity {

    protected Point position;
    protected Island island;
    @JsonProperty("id")
    private int id;

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public Entity(Point position, Island island, Integer id) {
        this.position = position;
        this.island = island;
        this.id = id;
    }
}
