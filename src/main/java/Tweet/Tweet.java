package Tweet;

/**
 * type field shows that what kind of tweet it is
 * it can be : "tweet", "retweet", "quote" and "reply"
 */
public class Tweet {

    private String text;
    private byte[] image;
    private byte[] video;
    private int likeNumber;
    private int reTweetNumber;
    private int replyNumber;
    private String sendingDate;
    private String tweetOwnerUsername;
    private String tweetType;
    private String hashtag;
}
