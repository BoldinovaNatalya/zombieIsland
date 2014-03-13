package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.database.DataBaseWorker;

import java.sql.SQLException;

public class LoginCommand extends Command {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";

    @Override
    public void execute() {
        Command result;
        DataBaseWorker dataBaseWorker = DataBaseWorker.getInstance();
        String username = parameters.get(USERNAME).toString();
        String password = parameters.get(PASSWORD).toString();
        try {
            if (dataBaseWorker.isRegistered(username, password)) {
                result = Command.Create(Command.LOGIN);
                //session.setAuthored(true);
            } else {
                result = Command.Create(Command.ERROR);
                parameters.put(MESSAGE, "Incorrect username/password");
            }
        } catch (SQLException e) {
            result = Command.Create(Command.ERROR);
            parameters.put(MESSAGE, "Data base error");
        }
        session.addToWriteQueue(result);
    }
}
