package Tweet;

import java.io.Serializable;

/**
 * type field shows that what kind of tweet it is
 * it can be : "tweet", "retweet", "quote" and "reply"
 */
public class Tweet implements Serializable {
    private int tweetId;
    private int parentTweetId;
    // this field is for when we want to sort tweets we use this field to compare and sort
    private long minutesDiff;
    private String timeDifference;
    private String text;
    private byte[] image;
    private byte[] video;
    private int likeNumber;
    private int reTweetNumber;
    private int replyNumber;
    private String sendingDate;
    private String sendingTime;
    private String tweetOwnerUsername;
    private String tweetType;
    private String hashtag;

    public Tweet(){
    }
    public Tweet(String text) {
        this.text = text;
    }


    public int getTweetId() {
        return tweetId;
    }
    public String getText() {
        return text;
    }
    public String getSendingDate() {
        return sendingDate;
    }
    public String getSendingTime() {
        return sendingTime;
    }

    public long getMinutesDiff() {
        return minutesDiff;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }
    public void setParentTweetId(int parentTweetId) {
        this.parentTweetId = parentTweetId;
    }
    public void setTimeDifference(String timeDifference) {
        this.timeDifference = timeDifference;
    }

    public void setMinutesDiff(long minutesDiff) {
        this.minutesDiff = minutesDiff;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
    public void setVideo(byte[] video) {
        this.video = video;
    }
    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }
    public void setReTweetNumber(int reTweetNumber) {
        this.reTweetNumber = reTweetNumber;
    }
    public void setReplyNumber(int replyNumber) {
        this.replyNumber = replyNumber;
    }
    public void setSendingDate(String sendingDate) {
        this.sendingDate = sendingDate;
    }
    public void setSendingTime(String sendingTime) {
        this.sendingTime = sendingTime;
    }
    public void setTweetOwnerUsername(String tweetOwnerUsername) {
        this.tweetOwnerUsername = tweetOwnerUsername;
    }
    public void setTweetType(String tweetType) {
        this.tweetType = tweetType;
    }
    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
