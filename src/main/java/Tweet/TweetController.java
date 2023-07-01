package Tweet;

import Client.User;
import Inheritance.ObjectStream;
import java.io.*;
import java.util.ArrayList;
import static UserApplicationSrarter.ORDER.*;

public class TweetController {

    public static ArrayList<User> searchUser(ObjectStream stream, String input){
        stream.WRITE(SearchUser + "");
        stream.WRITE(input);
        return stream.readUsersList();
    }

    public static ArrayList<Tweet> searchHashtags(ObjectStream stream, String hashtags){
        stream.WRITE(SearchHashtags + "");
        stream.WRITE(hashtags);
        return stream.readTweetsList();
    }


    public static ArrayList<Tweet> refreshTweets(ObjectStream stream) {
        stream.WRITE(RefreshTweets + "");
        return stream.readTweetsList();
    }

    public static void creatTweet(ObjectStream stream, String tweetText , String filePath) {
        Tweet tempTweet = new Tweet();
        if (filePath.trim().isEmpty()) {
            tempTweet.setText(tweetText);
        } else {
            try {
                File file = new File(filePath);
                byte[] imageData = new byte[(int) file.length()];
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(imageData);
                fileInputStream.close();
                tempTweet.setText(tweetText);
                tempTweet.setImage(imageData);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        stream.WRITE(CreateTweet + "");
        stream.writeTweet(tempTweet);
    }
}
