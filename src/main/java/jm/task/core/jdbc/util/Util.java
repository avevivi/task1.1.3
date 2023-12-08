package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // set up a database connection
    public static final String PASSWORD = "1111";
    public static final String USERNAME = "root";
    public static final String URL = "jdbc:mysql://localhost:3306/test";
    /* Без последнего /test проваливает тесты с ошибкой "no database selected"
       Наверное из-за того, что я запустил mysql в docker
       Я создал новый Database с названием test и тут положил путь к нему*/
    private Util() {}     // Utility class = private constructor

    public static Connection open() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
