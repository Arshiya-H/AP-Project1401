package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;


/**
 * This is a class to establish a connection with SQLite and enable us to work with JOOQ
 * */
 public class DBConnection {

    /**
     * This is address of our DB which is in our project directory
     * */
    private static final String url = "jdbc:sqlite:databaseTest.db";

    private Connection connection;
    private DSLContext DB;

    public DBConnection() {
        try {
            this.connection = DriverManager.getConnection(url);
            this.DB = DSL.using(this.connection, SQLDialect.SQLITE);
        }catch (SQLException e) {
            System.out.println("Connection with Data Base failed!");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public DSLContext getDB() {
        return DB;
    }


}
