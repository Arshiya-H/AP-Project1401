package UserApplicationSrarter;

public class ReTweet extends Tweet {
    private String whoReTweeted;

    public ReTweet(int like, int disLike, int reTweet, int comment, String userOfCreator, String whoReTweeted) {
        super(like, disLike, reTweet, comment, userOfCreator);
        this.whoReTweeted = whoReTweeted;
    }
}
