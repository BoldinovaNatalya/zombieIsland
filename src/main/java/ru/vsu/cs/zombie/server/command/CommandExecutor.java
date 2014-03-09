package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.database.DataBaseWorker;
import ru.vsu.cs.zombie.server.net.Session;

import java.sql.SQLException;

public abstract class CommandExecutor {

    private static DataBaseWorker dataBaseWorker = DataBaseWorker.getInstance();

    public static Command execute(Command command, Session session) {
        int id = command.getId();
        Command result = null;
        String username, password;
        try {
            switch (id) {
                case Command.HELLO:
                    result = new Command(Command.HELLO);
                    break;
                case Command.LOGIN:
                    username = command.getParameter("username").toString();
                    password = command.getParameter("password").toString();
                    try {
                        if (dataBaseWorker.isRegistered(username, password)) {
                            result = new Command(Command.LOGIN);
                        } else {
                            result = new Command(Command.ERROR);
                            result.putParameter("message", "Incorrect username/password");
                        }
                    } catch (SQLException e) {
                        result = new Command(Command.ERROR);
                        result.putParameter("message", "Data base error");
                    }
                    break;
                case Command.REGISTER:
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
}
