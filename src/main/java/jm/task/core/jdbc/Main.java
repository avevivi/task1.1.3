package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        // create table method
        // adding 4 USER data (with sout)
        // getting all users from table and sout (override toString)
        // clear table method
        // delete table method

        UserServiceImpl usi = new UserServiceImpl();

        usi.createUsersTable();
        usi.saveUser("firstN", "firstLN", (byte) 25);
        usi.saveUser("secondN", "secondLN", (byte) 25);
        usi.saveUser("thirdN", "thirdLN", (byte) 25);
        usi.saveUser("fourthN", "fourthLN", (byte) 25);
        usi.cleanUsersTable();
        usi.dropUsersTable();
    }
}
