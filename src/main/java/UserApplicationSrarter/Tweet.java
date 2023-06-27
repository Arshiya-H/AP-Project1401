package UserApplicationSrarter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Tweet {

    private int Like;
    private int disLike;
    private int reTweet;
    private int comment;
    private LocalDateTime date;
    private String userOfCreator;

    public Tweet(int like, int disLike, int reTweet, int comment, String userOfCreator) {
        Like = like;
        this.disLike = disLike;
        this.reTweet = reTweet;
        this.comment = comment;
        this.date = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd-hh:mm")));
        this.userOfCreator = userOfCreator;
    }

}
