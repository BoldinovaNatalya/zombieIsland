package ru.vsu.cs.zombie.server.logic.objects;


import com.fasterxml.jackson.annotation.JsonProperty;
import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

public class Resource extends Entity {

    private static final int HIDDEN_COORDINATE = -100;

    @JsonProperty("id")
    private int id;
    protected final int weight;
    private boolean isPickedUp = false;

    public Resource(Point position, Island island, int weight, Integer id) {
        super(position, island);
        this.weight = weight;
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void drop(Point position) {
        isPickedUp = false;
        this.position = position;
    }

    public void pickUp() {
        isPickedUp = true;
        position = new Point(HIDDEN_COORDINATE, HIDDEN_COORDINATE);
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }
}
