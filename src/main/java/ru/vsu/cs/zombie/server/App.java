package ru.vsu.cs.zombie.server;


import ru.vsu.cs.zombie.server.net.ZombieServer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 80;
        }
        ZombieServer server = new ZombieServer(port);
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
