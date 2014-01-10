package ru.vsu.cs.zombie.server.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class DataBaseWorker {

    private final static String CONNECTION_STRING = "jdbc:h2:tcp://localhost/~/test user=ZOMBIE password=conw12q";

    private static DataBaseWorker instance = null;

    public static DataBaseWorker getInstance() {
        if (instance == null) {
            instance = new DataBaseWorker();
        }
        return instance;
    }

    private Dao<User, Integer> userDao;

    private DataBaseWorker() {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(CONNECTION_STRING);
            userDao = DaoManager.createDao(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean register(String username, String password) throws SQLException {
        if (!usernameAlreadyExists(username)) {
            User user = new User(username, password);
            userDao.create(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean isRegistered(String username, String password) throws SQLException {
        User user = new User(username, password);
        QueryBuilder<User, Integer> queryBuilder = userDao.queryBuilder();
        queryBuilder.where().eq(User.NAME, username).and().eq(User.PASSWORD, password);
        List<User> users = userDao.query(queryBuilder.prepare());
        return users.size() == 1;
    }

    private boolean usernameAlreadyExists(String username) throws SQLException {
        QueryBuilder<User, Integer> queryBuilder = userDao.queryBuilder();
        queryBuilder.where().eq(User.NAME, username);
        List<User> users = userDao.query(queryBuilder.prepare());
        return users.size() > 0;
    }
}
