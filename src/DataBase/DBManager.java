package DataBase;

import org.jooq.DSLContext;
import java.sql.SQLException;
import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.VARCHAR;

/**
 * This class is to interact with database file.
 * It has methods which acts as Queries.
 * */

public class DBManager {

    public static void creatUsersTable(){
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();

        // Use jOOQ to DB a table
        DB.createTableIfNotExists("Users")
                .column("userName", VARCHAR(255))
                .column("firstName", VARCHAR(255))
                .column("lastName", VARCHAR(255))
                .column("email", VARCHAR(255))
                .column("phoneNumber",VARCHAR(255))
                .column("password",VARCHAR(255))
                .column("country",VARCHAR(255))
                .column("birthDate", VARCHAR(255))
                .column("inComeDate", VARCHAR(255))
                .column("lastChangeDate",VARCHAR(255))
                .column("bio",VARCHAR(255))
                .column("location",VARCHAR(255))
                .column("webAddress",VARCHAR(255))
                .column("JWT",VARCHAR(255))
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
     * */
    public static boolean checkUserName(String userName)  {
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
        return usernameExists;
    }
    /**
     * @return boolean
     * True: if,  email has existed before
     * False: if, email does not exist
     * */
    public static boolean checkEmail(String email)  {
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
        return usernameExists;
    }
    /**
     * @return boolean
     * True: if,  phoneNumber has existed before
     * False: if, phoneNumber does not exist
     * */
    public static boolean checkPhoneNumber(String phoneNumber)  {
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
        return usernameExists;
    }
    //--------------------------------------------------------------------------------
    // Insert Date:
    public static void insertUserToDB(String userName, String firstName, String lastName, String email, String phoneNumber, String password
            , String country, String birthDate, String inComeDate, String lastChangeDate, String bio, String location, String webAddress, String JWT){

        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();

        DB.insertInto(table("Users")).values(userName,firstName,lastName,email,phoneNumber,password,country,birthDate,inComeDate,lastChangeDate
                ,bio,location,webAddress,JWT).execute();

        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------
}
