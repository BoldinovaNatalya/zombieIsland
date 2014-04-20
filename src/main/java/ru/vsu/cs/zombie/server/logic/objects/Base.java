package ru.vsu.cs.zombie.server.logic.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class Base extends Building {

    @JsonProperty("resources")
    private Set<Resource> resources = new TreeSet<Resource>();

    public Base(Point position, Island island) {
        super(position, island);
    }

    public Set<Resource> getResources() {
        return Collections.unmodifiableSet(resources);
    }

    public void add(Resource resource) {
        resources.add(resource);
        resource.pickUp();
    }

    public void remove(Resource resource) {
        resources.remove(resource);
        resource.drop(getPosition());
    }
}
