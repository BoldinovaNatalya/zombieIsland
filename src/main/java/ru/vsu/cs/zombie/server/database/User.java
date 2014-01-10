package ru.vsu.cs.zombie.server.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User {

    public static final String NAME = "name";
    public static final String PASSWORD = "password";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    @DatabaseField(canBeNull = false)
    private String password;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    //empty constructor for ORMLite
    private User() {

    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
