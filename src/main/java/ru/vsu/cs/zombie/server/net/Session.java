package ru.vsu.cs.zombie.server.net;

import io.netty.channel.Channel;
import ru.vsu.cs.zombie.server.command.Command;
import ru.vsu.cs.zombie.server.logic.Island;

public class Session {

    private Channel channel = null;

    private boolean isAuthorized = false;

    private Island island = null;

    private ZombieServer server;

    public Session(Channel channel, ZombieServer server) {
        this.channel = channel;
        this.server = server;
    }

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    public Channel getChannel() {
        return channel;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public void process(Command command) {
        command.setSession(this);
        server.process(command);
    }

    public void write(Command command) {
        server.write(command);
    }
}
