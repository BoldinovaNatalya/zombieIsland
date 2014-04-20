package ru.vsu.cs.zombie.server.logic.objects;

import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.logic.Point;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class Base extends Building {

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
