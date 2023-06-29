package Tweet;

import java.io.Serializable;

/**
 * type field shows that what kind of tweet it is
 * it can be : "tweet", "retweet", "quote" and "reply"
 */
public class Tweet implements Serializable {
    private int tweetId;
    private int timeDifference;
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

    public Tweet(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
}
