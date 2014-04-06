package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.database.DataBaseWorker;

import java.sql.SQLException;

public class RegisterCommand extends Command {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Override
    public void execute() {
        Command result;
        DataBaseWorker dataBaseWorker = DataBaseWorker.getInstance();
        String username = parameters.get(USERNAME).toString();
        String password = parameters.get(PASSWORD).toString();
        try {
            if (dataBaseWorker.register(username, password)) {
                result = createResponse();
            } else {
                result = new ErrorCommand("Registration failed", id);
            }
        } catch (SQLException e) {
            result = new ErrorCommand("Data base error", id);
        }
        session.addToWriteQueue(result);
    }
}
