package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    public static final String PASSWORD = "1111";
    public static final String USERNAME = "root";
    public static final String URL = "jdbc:mysql://localhost:3306";

    private Util() {
    }

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


    private static final SessionFactory sessionFactory;
    
    static {
        try {
            Properties props = new Properties();
            props.setProperty("hibernate.driver_class", "com.mysql.cj.jdbc.Driver");
            props.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/test");
            props.setProperty("hibernate.connection.username", "root");
            props.setProperty("hibernate.connection.password", "1111");
            props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            props.setProperty("hibernate.show_sql", "true");
            props.setProperty("hibernate.format_sql", "true");

            Configuration cfg = new Configuration();
            cfg.setProperties(props);
            cfg.addAnnotatedClass(User.class);
            sessionFactory = cfg.buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session openSession() {
        return sessionFactory.openSession();
    }

    public static void closeSession(Session session) {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}