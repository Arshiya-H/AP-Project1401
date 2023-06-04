package DataBase;

import org.jooq.DSLContext;
import org.jooq.Record;

import java.sql.SQLException;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.VARCHAR;

/**
 * This class is to interact with database file.
 * It has methods which acts as Queries.
 */

public class DBManager {

    public static void creatUsersTable() {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();

        // Use jOOQ to DB a table
        DB.createTableIfNotExists("Users")
                .column("userName", VARCHAR(255))
                .column("firstName", VARCHAR(255))
                .column("lastName", VARCHAR(255))
                .column("email", VARCHAR(255))
                .column("phoneNumber", VARCHAR(255))
                .column("password", VARCHAR(255))
                .column("country", VARCHAR(255))
                .column("birthDate", VARCHAR(255))
                .column("inComeDate", VARCHAR(255))
                .column("lastChangeDate", VARCHAR(255))
                .column("bio", VARCHAR(255))
                .column("location", VARCHAR(255))
                .column("webAddress", VARCHAR(255))
                .column("JWT", VARCHAR(255))
                .column("secretKey", VARCHAR(255))
                .execute();
        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return boolean
     * True: if,  username has existed before
     * False: if, username does not exist
     */
    public static boolean checkUserName(String userName) {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();

        boolean usernameExists = DB.fetchExists(
                DB.selectOne()
                        .from("Users")
                        .where("userName = ?", userName)
        );
        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (usernameExists) {
            return true;
        }
        return false;
    }

    /**
     * @return boolean
     * True: if,  email has existed before
     * False: if, email does not exist
     */
    public static boolean checkEmail(String email) {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();

        boolean usernameExists = DB.fetchExists(
                DB.selectOne()
                        .from("Users")
                        .where("email = ?", email)
        );
        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (usernameExists) {
            System.out.println("the email has existed already, choose another :");
            return true;
        }
        return false;
    }

    /**
     * @return boolean
     * True: if,  phoneNumber has existed before
     * False: if, phoneNumber does not exist
     */
    public static boolean checkPhoneNumber(String phoneNumber) {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();

        boolean usernameExists = DB.fetchExists(
                DB.selectOne()
                        .from("Users")
                        .where("phoneNumber = ?", phoneNumber)
        );
        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (usernameExists) {
            System.out.println("the phone number has existed already, choose another :");
            return true;
        }
        return false;
    }

    public static boolean checkSecurityKay(String secretKey) {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();

        boolean usernameExists = DB.fetchExists(
                DB.selectOne()
                        .from("Users")
                        .where("secretKey = ?", secretKey));
        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (usernameExists) {
            return true;
        }
        return false;
    }

    // Insert Date:
    public static void insertUserToDB(String userName, String firstName, String lastName, String email, String phoneNumber, String password
            , String country, String birthDate, String inComeDate, String lastChangeDate) {

        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();

        DB.insertInto(table("Users"), field("userName"), field("firstName"), field("lastName"), field("email"),
                        field("phoneNumber"), field("password"), field("country"), field("birthDate"), field("inComeDate"),
                        field("lastChangeDate"))
                .values(userName, firstName, lastName, email, phoneNumber, password, country, birthDate, inComeDate, lastChangeDate).execute();

        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateSecretKeyAndJWT(String userName, String secretKey, String jwt) {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();

        DB.update(table("Users"))
                .set(field("secretKey"), secretKey)
                .set(field("JWT"), jwt)
                .where(field("userName").eq(userName))
                .execute();
        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkPass(String userName, String pass) {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();

        Record record = DB.select(field("password"))
                .from(table("Users"))
                .where(field("userName").eq(userName))
                .fetchOne();

        String password = null;
        if (record != null) password = record.getValue(field("password"), String.class);

        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (pass.equals(password)) {
            return true;
        }
        return false;
    }
}
