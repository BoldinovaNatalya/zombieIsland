package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.database.DataBaseWorker;
import ru.vsu.cs.zombie.server.logic.Island;
import ru.vsu.cs.zombie.server.net.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class CommandExecutor {

    private static DataBaseWorker dataBaseWorker = DataBaseWorker.getInstance();

    public static Command execute(Command command, Session session) {
        int id = command.getId();
        Command result = null;
        try {
            switch (id) {
                case Command.HELLO:
                    result = new Command(Command.HELLO);
                    break;
                case Command.LOGIN:
                    result = executeLoginCommand(command, session);
                    break;
                case Command.REGISTER:
                    result = executeRegisterCommand(command);
                    break;
                case Command.GET_ISLANDS:
                    result = executeGetIslandsCommand(command);
                    break;
                case Command.CREATE_ISLAND:
                    result = executeCreateIslandCommand(command, session);
                    break;
                case Command.JOIN_ISLAND:
                    result = executeJoinIslandCommand(command, session);
                    break;
                default:
                    result = new Command(Command.ERROR);
                    result.putParameter("message", "No such command");
                    result.putParameter("command_id", id);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new Command(Command.ERROR);
            result.putParameter("message", "Some error");
        }
        return result;
    }

    private static Command executeJoinIslandCommand(Command command, Session session) {
        int id = (Integer)command.getParameter("island_id");
        Island island = Island.getIsland(id);
        island.addSession(session);
        return new Command(Command.JOIN_ISLAND);
    }

    private static Command executeCreateIslandCommand(Command command, Session session) {
        Command result;
        int players = (Integer)command.getParameter("players");
        Island island = Island.CreateIsland(players);
        island.addSession(session);
        result = new Command(Command.CREATE_ISLAND);
        return result;
    }

    private static Command executeRegisterCommand(Command command) {
        String username;
        String password;
        Command result;
        username = command.getParameter("username").toString();
        password = command.getParameter("password").toString();
        try {
            if (dataBaseWorker.register(username, password)) {
                result = new Command(Command.REGISTER);
            } else {
                result = new Command(Command.ERROR);
                result.putParameter("message", "Registration failed");
            }
        } catch (SQLException e) {
            result = new Command(Command.ERROR);
            result.putParameter("message", "Data base error");
        }
        return result;
    }

    private static Command executeLoginCommand(Command command, Session session) {
        String username;
        String password;
        Command result;
        username = command.getParameter("username").toString();
        password = command.getParameter("password").toString();
        try {
            if (dataBaseWorker.isRegistered(username, password)) {
                result = new Command(Command.LOGIN);
                //session.setAuthored(true);
            } else {
                result = new Command(Command.ERROR);
                result.putParameter("message", "Incorrect username/password");
            }
        } catch (SQLException e) {
            result = new Command(Command.ERROR);
            result.putParameter("message", "Data base error");
        }
        return result;
    }

    private static Command executeGetIslandsCommand(Command command) {
        Command result = new Command(Command.GET_ISLANDS);
        List<Object> islands = new ArrayList<Object>();
        int i = 0;
        for (Island island : Island.getIslands()) {
            Map<String, Integer> islandInfo = new TreeMap<String, Integer>();
            islandInfo.put("island_id", i++);
            islandInfo.put("max_players", island.getPlayerCount());
            islandInfo.put("current_players", island.getSessions().size());
            islands.add(islandInfo);
        }
        result.putParameter("islands", islands);
        return result;
    }
}
