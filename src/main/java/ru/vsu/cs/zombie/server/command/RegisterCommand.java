package ru.vsu.cs.zombie.server.command;

import ru.vsu.cs.zombie.server.database.DataBaseWorker;

import java.sql.SQLException;

public class RegisterCommand extends Command {

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
            if (dataBaseWorker.register(username, password)) {
                result = Command.create(Command.REGISTER);
            } else {
                result = Command.create(Command.ERROR);
                parameters.put(MESSAGE, "Registration failed");
            }
        } catch (SQLException e) {
            result = Command.create(Command.ERROR);
            parameters.put(MESSAGE, "Data base error");
        }
        session.addToWriteQueue(result);
    }
}
