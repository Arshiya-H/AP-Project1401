package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;


/**
 * This is a class to establish a connection with SQLite and enable us to work with JOOQ
 * */

abstract public class DBConnection {

    /**
     * This is address of our DB which is in our project directory
     * */
    private static final String url = "jdbc:sqlite:databaseTest.db";

    /**
     * @return HashMap
     * Key: Connection to sqlite.And whenever our work is done we can close connection with it
     * Value: DSLContext enable us to work with JOOQ functions
     */
    public static HashMap<Connection,DSLContext> returnDBConnections() {

        // Create a connection to the SQLite database
        try {
            Connection conn = DriverManager.getConnection(url);
            // Create a jOOQ DSLContext object with the SQLite dialect
            DSLContext DB = DSL.using(conn, SQLDialect.SQLITE);
            HashMap<Connection, DSLContext> dbConnections = new HashMap<>();
            dbConnections.put(conn, DB);
            return dbConnections;
        } catch (SQLException e) {
            System.out.println("Connection with Data Base failed!");
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @param dbConnections HashMap which contains sqlite connection as key and its DSLContext as value
     * @return Connection returns given HashMap key
     * */
    public static Connection returnConnection(HashMap<Connection,DSLContext> dbConnections){
        Connection connection = null;
        assert dbConnections != null;
        for (Connection con : dbConnections.keySet()) {
            connection = con;
        }
        return connection;
    }
}
