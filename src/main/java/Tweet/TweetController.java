package Tweet;

import Client.User;
import Inheritance.ObjectStream;

import java.util.ArrayList;

import static UserApplicationSrarter.ORDER.*;

public class TweetController {

    public static ArrayList<User> searchUser(ObjectStream stream, String input) {
        stream.WRITE(SearchUser + "");
        stream.WRITE(input);
        return stream.readUsersList();
    }

    public static ArrayList<Tweet> searchHashtags(ObjectStream stream, String hashtags) {
        stream.WRITE(SearchHashtags + "");
        stream.WRITE(hashtags);
        return stream.readTweetsList();
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

    public static int[] hashtagsStatics(ObjectStream stream, String hashtag) {
        stream.WRITE(HashtagsStatics + "");
        stream.WRITE(hashtag);
        ArrayList<Tweet> wantedTweets = stream.readTweetsList();
        ArrayList<Tweet> allTweets = stream.readTweetsList();
        int[] statics = new int[2];
        statics[0] = wantedTweets.size();
        statics[1] = allTweets.size();
        return statics;
    }
}
