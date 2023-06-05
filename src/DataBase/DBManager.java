package DataBase;

import org.jooq.DSLContext;
import org.jooq.Record;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.BLOB;
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
                .column("avatar", BLOB)
                .column("header", BLOB)
                .execute();
        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //------------------------------------------------------------------------------------
    // A table in database for handling following ande followers:
    public static  void  creatFollowingTable(){
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();

        DB.createTableIfNotExists("Followings")
                .column("userName",VARCHAR(255))
                .column("userNameFollowed",VARCHAR(255))
                .execute();
        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //------------------------------------------------------------------------------------
    //If you want to follow:
    /**
     * This method is for checking if user we want to follow has already followed or not
     * @return boolean
     * True: if user we want to follow in "follow" method has already followed
     * False: if user we want to follow han not followed yet
     * */
    private static boolean checkUserFollowed(String userNameFollowed, ArrayList<String> selectedUsers){
        for (String selectedUser : selectedUsers) {
            if (selectedUser.equals(userNameFollowed)) {
                return true;
            }
        }
        return false;
    }
    public static void follow(String userName , String userNameFollowed)  {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        DSLContext DB = dbConnection.getDB();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT userNameFollowed FROM Followings WHERE userName = ? ");
            statement.setString(1,userName);
            ResultSet result = statement.executeQuery();
            if (result.next()) {

                ArrayList<String> selectedUsers = new ArrayList<>();
                while(result.next()){
                    String tempUsername = result.getString("userNameFollowed");
                    selectedUsers.add(tempUsername);
                }
                if (checkUserFollowed(userNameFollowed,selectedUsers)) {
                    System.out.println("You've already followed this user!");
                    connection.close();
                    return;
                }
            } else {
                System.out.println("Invalid username!");
                connection.close();
                return;
            }
        }catch (SQLException e){
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        DB.insertInto(table("Followings"), field("userName"), field("userNameFollowed")).values(userName,userNameFollowed).execute();
    }
    public static void  unFollow(String userName , String userNameFollowed){
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();

        try {
            PreparedStatement stm = connection.prepareStatement("DELETE FROM Followings WHERE userName = ? AND userNameFollowed = ?");
            stm.setString(1,userName);
            stm.setString(2,userNameFollowed);
            int rowsAffected = stm.executeUpdate();
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

    }
    //------------------------------------------------------------------------------------


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

    public static void updatePhoneNumber(String userName, String phoneNumber) {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();
        DB.update(table("Users"))
                .set(field("phoneNumber"), phoneNumber)
                .where(field("userName").eq(userName))
                .execute();
        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEmail(String userName, String email) {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();
        DB.update(table("Users"))
                .set(field("email"), email)
                .where(field("userName").eq(userName))
                .execute();
        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBio(String userName, String bio) {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();
        DB.update(table("Users"))
                .set(field("bio"), bio)
                .where(field("userName").eq(userName))
                .execute();
        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateLocation(String userName, String location) {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();
        DB.update(table("Users"))
                .set(field("location"), location)
                .where(field("userName").eq(userName))
                .execute();
        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePassword(String userName, String pass) {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();
        DB.update(table("Users"))
                .set(field("password"), pass)
                .where(field("userName").eq(userName))
                .execute();
        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateWebAddress(String userName, String webAddress) {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();
        DB.update(table("Users"))
                .set(field("webAddress"), webAddress)
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

    // subject : is the field we want to change (header or avatar)
    //       ************** Attention!! **************
    public static void updateAvatarOrHeader(String path, String userName,String subject) {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();
        try {
            BufferedImage image = ImageIO.read(new File(path));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] imageData = baos.toByteArray();
            DB.update(table("Users"))
                    .set(field(subject), imageData)
                    .where(field("userName").eq(userName))
                    .execute();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }



}
