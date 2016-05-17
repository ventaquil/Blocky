package sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public abstract class Manager {
    public static void execute(String database, String query) throws ClassNotFoundException, SQLException
    {
        Connection connection = open(database);

        execute(connection, query);

        connection.close();
    }

    public static void execute(Connection connection, String query) throws SQLException
    {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

    public static Connection open(String database) throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");

        return DriverManager.getConnection("jdbc:sqlite:" + database + ".db");
    }

    public static ResultSet select(Statement statement, String query) throws SQLException
    {
        return statement.executeQuery(query);
    }

    public static void tryExecute(String database, String query)
    {
        try {
            execute(database, query);
        } catch (ClassNotFoundException|SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void tryExecute(Connection connection, String query)
    {
        try {
            execute(connection, query);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static Connection tryOpen(String database)
    {
        try {
            return open(database);
        } catch (ClassNotFoundException|SQLException e) {
            return null;
        }
    }
};
