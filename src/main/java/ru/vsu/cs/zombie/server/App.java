package ru.vsu.cs.zombie.server;


import ru.vsu.cs.zombie.server.net.ZombieServer;

public class App {

    private final static int DEFAULT_PORT = 900;

    public static void main(String[] args) {
        int port;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                port = DEFAULT_PORT;
            }
        } else {
            port = DEFAULT_PORT;
        }
        ZombieServer server = new ZombieServer(port);
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
