package Tweet;

import Inheritance.ObjectStream;

import static UserApplicationSrarter.ORDER.*;

public class TweetController {

    public static void creatTweet(ObjectStream stream , String tweetText ){
        Tweet tempTweet = new Tweet(tweetText);
        stream.WRITE(CreateTweet+"");
        stream.writeTweet(tempTweet);
    }
}
