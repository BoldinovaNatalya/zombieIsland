package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.database.DataBaseWorker;

import java.sql.SQLException;

public class LoginCommand extends Command {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Override
    public void execute() {
        Command result;
        DataBaseWorker dataBaseWorker = DataBaseWorker.getInstance();
        String username = parameters.get(USERNAME).toString();
        String password = parameters.get(PASSWORD).toString();
        try {
            if (dataBaseWorker.isRegistered(username, password)) {
                result = createResponse();
                session.setAuthorized(true);
            } else {
                result = new ErrorCommand("Incorrect username/password", id);
            }
        } catch (SQLException e) {
            result = new ErrorCommand("Data base error", id);
        }
        session.addToWriteQueue(result);
    }
}
