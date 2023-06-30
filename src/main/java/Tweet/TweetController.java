package Tweet;

import Client.User;
import Inheritance.ObjectStream;
import java.util.ArrayList;
import static UserApplicationSrarter.ORDER.*;

public class TweetController {
    public static ArrayList<User> searchUser(ObjectStream stream, String input){
        stream.WRITE(SearchUser + "");
        stream.WRITE(input);
        return stream.readUsersList();
    }

    public static ArrayList<Tweet> refreshTweets(ObjectStream stream) {
        stream.WRITE(RefreshTweets + "");
        return stream.readTweetsList();
    }

    public static void creatTweet(ObjectStream stream, String tweetText) {
        Tweet tempTweet = new Tweet(tweetText);
        stream.WRITE(CreateTweet + "");
        stream.writeTweet(tempTweet);
    }
}
