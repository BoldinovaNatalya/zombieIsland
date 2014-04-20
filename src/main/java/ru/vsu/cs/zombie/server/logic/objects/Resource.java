package ru.vsu.cs.zombie.server.logic.objects;


import com.fasterxml.jackson.annotation.JsonProperty;
import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public abstract class Resource extends Entity {

    private static final int HIDDEN_COORDINATE = -100;

    @JsonProperty("id")
    private int id;
    protected final int weight;
    private boolean isUsed = false;

    public Resource(Point position, Island island, int weight, Integer id) {
        super(position, island);
        this.weight = weight;
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void drop(Point position) {
        this.position = position;
    }

    public void pickUp() {
        position = new Point(HIDDEN_COORDINATE, HIDDEN_COORDINATE);
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void use(Man man) {
        isUsed = true;
    };
}
