package DataBase;

import Client.User;
import Tweet.Tweet;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.QOM;

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

    // Tables:
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

    /**
     * A table for saving tweets
     * note: if "parentTweetId" be -1 means it has no parent and, it is a pure tweet
     */
    public static void creatTweetsTable() {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Tweets (id INTEGER PRIMARY KEY, likeNumber INTEGER DEFAULT 0" +
                    ", reTweetNumber INTEGER DEFAULT 0, replyNumber INTEGER DEFAULT 0, parentTweetId INTEGER, sendingDate TEXT,sendingTime TEXT,tweetOwnerUsername TEXT, tweetType TEXT" +
                    ",text TEXT,image BLOB, video BLOB, hashtag TEXT)");
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // A table in database for handling following ande followers:
    public static void creatFollowingTable() {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();

        DB.createTableIfNotExists("Followings")
                .column("userName", VARCHAR(255))
                .column("userNameFollowed", VARCHAR(255))
                .execute();
        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------
    // Insert Data:
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

    public static void insertTweetToDB(String text, byte[] image, byte[] video, int parentTweetId, String sendingDate
            , String sendingTime, String tweetOwnerUsername, String tweetType, String hashtag) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Tweets(parentTweetId,sendingDate,sendingTime,tweetOwnerUsername,tweetType,text,image,video,hashtag) VALUES(?,?,?,?,?,?,?,?,?)");

            statement.setInt(1, parentTweetId);
            statement.setString(2, sendingDate);
            statement.setString(3, sendingTime);
            statement.setString(4, tweetOwnerUsername);
            statement.setString(5, tweetType);
            statement.setString(6, text);
            statement.setBytes(7, image);
            statement.setBytes(8, video);
            statement.setString(9, hashtag);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    //------------------------------------------------------------------------------------
    // Follow Things:

    /**
     * This method is for checking if user we want to follow has already followed or not
     *
     * @return boolean
     * True: if user we want to follow in "follow" method has already followed
     * False: if user we want to follow han not followed yet
     */
    private static boolean checkUserFollowed(String userNameFollowed, ArrayList<String> selectedUsers) {
        for (String selectedUser : selectedUsers) {
            if (selectedUser.equals(userNameFollowed)) {
                return true;
            }
        }
        return false;
    }

    public static void follow(String userName, String userNameFollowed) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        DSLContext DB = dbConnection.getDB();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT userNameFollowed FROM Followings WHERE userName = ? ");
            statement.setString(1, userName);
            ResultSet result = statement.executeQuery();
            if (result.next()) {

                ArrayList<String> selectedUsers = new ArrayList<>();
                while (result.next()) {
                    String tempUsername = result.getString("userNameFollowed");
                    selectedUsers.add(tempUsername);
                }
                if (checkUserFollowed(userNameFollowed, selectedUsers)) {
                    System.out.println("You've already followed this user!");
                    connection.close();
                    return;
                }
            } else {
                System.out.println("Invalid username!");
                connection.close();
                return;
            }
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        DB.insertInto(table("Followings"), field("userName"), field("userNameFollowed")).values(userName, userNameFollowed).execute();
    }

    public static void unFollow(String userName, String userNameFollowed) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();

        try {
            PreparedStatement stm = connection.prepareStatement("DELETE FROM Followings WHERE userName = ? AND userNameFollowed = ?");
            stm.setString(1, userName);
            stm.setString(2, userNameFollowed);
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

    public static ArrayList<String> getFollowings(String userName) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        ArrayList<String> followingUsers = new ArrayList<>();

        try {
            PreparedStatement stm = connection.prepareStatement("SELECT userNameFollowed FROM Followings WHERE userName = ?");
            stm.setString(1, userName);
            ResultSet result = stm.executeQuery();

            while (result.next()) {
                String tempUsers = result.getString("userNameFollowed");
                followingUsers.add(tempUsers);
            }
            return followingUsers;

        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        // *******************"NullPointerException"*******************
        return null;
    }

    public static ArrayList<String> getFollowers(String userName) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        ArrayList<String> followerUsers = new ArrayList<>();

        try {
            PreparedStatement stm = connection.prepareStatement("SELECT userName FROM Followings WHERE userNameFollowed = ?");
            stm.setString(1, userName);
            ResultSet result = stm.executeQuery();

            while (result.next()) {
                String tempUsers = result.getString("userName");
                followerUsers.add(tempUsers);
            }
            return followerUsers;

        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        // *******************"NullPointerException"*******************
        return null;
    }

    //------------------------------------------------------------------------------------
    // Tweet things:
    public static ArrayList<Tweet> getAllTweets() throws RuntimeException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Tweets");
            ResultSet resultSet = pstmt.executeQuery();

            ArrayList<Tweet> allTweets = new ArrayList<>();
            while (resultSet.next()) {
                Tweet tempTweet = new Tweet();
                tempTweet.setTweetId(resultSet.getInt("id"));
                tempTweet.setLikeNumber(resultSet.getInt("likeNumber"));
                tempTweet.setReTweetNumber(resultSet.getInt("reTweetNumber"));
                tempTweet.setReplyNumber(resultSet.getInt("replyNumber"));
                tempTweet.setParentTweetId(resultSet.getInt("parentTweetId"));
                tempTweet.setImage(resultSet.getBytes("image"));
                tempTweet.setVideo(resultSet.getBytes("video"));
                tempTweet.setText(resultSet.getString("text"));
                tempTweet.setSendingDate(resultSet.getString("sendingDate"));
                tempTweet.setSendingTime(resultSet.getString("sendingTime"));
                tempTweet.setTweetOwnerUsername(resultSet.getString("tweetOwnerUsername"));
                tempTweet.setTweetOwnerFullName(DBManager.returnFullName(resultSet.getString("tweetOwnerUsername")));
                tempTweet.setTweetType(resultSet.getString("tweetType"));
                tempTweet.setHashtag(resultSet.getString("hashtag"));
                allTweets.add(tempTweet);
            }
            return allTweets;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
    //------------------------------------------------------------------------------------
    // Search things:

    /**
     * this method search by username or full name
     * you should write in one of these two patterns:
     * "UserName" or "first_name last_name"
     */
    public static ArrayList<User> searchUser(String input) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();

        String[] splitInput = input.split(" ");
        int cnt = splitInput.length;
        PreparedStatement statement = null;
        try {
            // search fo username:
            if (cnt == 1) {
                // search fo username
                statement = connection.prepareStatement("SELECT * FROM Users WHERE userName = ?");
                statement.setString(1, splitInput[0]);
            }
            // search for full name:
            else if (cnt == 2) {
                // search for first and last names
                statement = connection.prepareStatement("SELECT * FROM Users WHERE firstName = ? AND lastName = ?");
                statement.setString(1, splitInput[0]);
                statement.setString(2, splitInput[1]);
            }

            ResultSet rs = statement.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No Result!");
                return null;
            }

            ArrayList<User> users = new ArrayList<>();
            while (rs.next()) {
                User tempUser = new User();
                tempUser.setUserName(rs.getString("userName"));
                tempUser.setFirstName(rs.getString("firstName"));
                tempUser.setLastName(rs.getString("lastName"));
                tempUser.setEmail(rs.getString("email"));
                tempUser.setPhoneNumber(rs.getString("phoneNumber"));
                tempUser.setPassword(rs.getString("password"));
                tempUser.setCountry(rs.getString("country"));
                tempUser.setBirthDate(rs.getString("birthDate"));
                tempUser.setInComeDate(rs.getString("inComeDate"));
                tempUser.setLastChangeDate(rs.getString("lastChangeDate"));
                tempUser.setBio(rs.getString("bio"));
                tempUser.setLocation(rs.getString("location"));
                tempUser.setWebAddress(rs.getString("webAddress"));
                tempUser.setJWT(rs.getString("JWT"));
                tempUser.setAvatar(rs.getBytes("avatar"));
                tempUser.setHeader(rs.getBytes("header"));
                users.add(tempUser);
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * this method search by given hashtag
     * you should write in this pattern:
     * "#your_hashtag"
     */
    public static ArrayList<Tweet> searchHashtags(String hashtag) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();

        try {

            String query = "SELECT * FROM Tweets WHERE hashtag LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + hashtag + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Tweet> foundTweets = new ArrayList<>();
            while (resultSet.next()) {
                Tweet tempTweet = new Tweet();
                tempTweet.setTweetId(resultSet.getInt("id"));
                tempTweet.setLikeNumber(resultSet.getInt("likeNumber"));
                tempTweet.setReTweetNumber(resultSet.getInt("reTweetNumber"));
                tempTweet.setReplyNumber(resultSet.getInt("replyNumber"));
                tempTweet.setParentTweetId(resultSet.getInt("parentTweetId"));
                tempTweet.setImage(resultSet.getBytes("image"));
                tempTweet.setVideo(resultSet.getBytes("video"));
                tempTweet.setText(resultSet.getString("text"));
                tempTweet.setSendingDate(resultSet.getString("sendingDate"));
                tempTweet.setSendingTime(resultSet.getString("sendingTime"));
                tempTweet.setTweetOwnerUsername(resultSet.getString("tweetOwnerUsername"));
                tempTweet.setTweetType(resultSet.getString("tweetType"));
                tempTweet.setHashtag(resultSet.getString("hashtag"));
                foundTweets.add(tempTweet);
            }
            return foundTweets;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String returnFullName(String userName){
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();

        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT firstName, lastName FROM Users WHERE userName = ?");
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                return firstName.concat(" "+lastName);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
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

    public static void updatePhoneNumber(String phoneNumber, String userName) {
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

    public static void updateEmail(String email, String userName) {
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

    public static void updateBio(String bio, String userName) {
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

    public static void updateLocation(String location, String userName) {
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

    public static void updateSubjectUser(String Subject, String userName, String update) {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();
        DB.update(table("Users"))
                .set(field(Subject), update)
                .where(field("userName").eq(userName))
                .execute();
        try {
            dbConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePassword(String pass, String userName) {
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

    public static void updateWebAddress(String webAddress, String userName) {
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
    public static void updateAvatarOrHeader(String path, String userName, String subject) {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();
        try {
            String[] splittedString = path.split("\\.");
            int index = splittedString.length;
            String imageFormat = splittedString[index - 1];
            BufferedImage image = ImageIO.read(new File(path));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, imageFormat, baos);
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
