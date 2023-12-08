package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    //Создание таблицы для пользователей - не должно вызывать исключение, если такая таблица уже существует.
    //Удаление таблицы для пользователей - не должно вызывать исключение, если таблица не существует.

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255) NOT NULL, last_name VARCHAR(255) NOT NULL, age TINYINT NOT NULL)";
    private static final String DROP_USERS_TABLE_SQL = "DROP TABLE IF EXISTS users";
    private static final String SAVE_USER_SQL = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
    private static final String REMOVE_USER_BY_ID_SQL = "DELETE FROM users WHERE id = ?";
    private static final String GET_ALL_USERS_SQL = "SELECT * FROM users";
    private static final String CLEAN_USERS_TABLE_SQL = "DELETE FROM users";


    public UserDaoJDBCImpl() {
        // has to be empty
    }

    public void createUsersTable() {
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_SQL);
            System.out.println("Users table created.");
        } catch (SQLException e) {
            System.err.println("Table already exists.");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_USERS_TABLE_SQL);
            System.out.println("Users table dropped.");
        } catch (SQLException e) {
            System.err.println("Table doesn't exist.");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_SQL)) {
            User user = new User(name, lastName, age);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

            System.out.println(user.toString() + "\nhas been added.\n");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS_SQL);

            while (resultSet.next()) {
                users.add(getDataFromUser(resultSet));
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CLEAN_USERS_TABLE_SQL);
            System.out.println("Users table cleaned.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User getDataFromUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setAge(resultSet.getByte("age"));
        return user;
    }
}
