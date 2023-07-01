package Server;

import Client.User;
import DataBase.DBManager;
import Tweet.Tweet;
import UserApplicationSrarter.ORDER;
import Inheritance.ObjectStream;

import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectionApp implements Runnable {

    private String JWT;
    private String userName;
    private ObjectStream serverObjectStream;
    private Socket socket;

    public ConnectionApp(ObjectStream serverObjectStream, Socket socket) {
        this.serverObjectStream = serverObjectStream;
        this.socket = socket;
    }


    @Override
    public void run() {
        while (socket.isConnected()) {
            switch (ORDER.valueOf(serverObjectStream.READ())) {
                case CheckUserName -> serverObjectStream.WRITE(checkUserName(serverObjectStream.READ()) + "");
                case PhoneNumber -> phoneNumber(serverObjectStream.READ());
                case Email -> email(serverObjectStream.READ());
                case CheckPass -> serverObjectStream.WRITE(checkPass(serverObjectStream.READ()) + "");
                case InsertUser -> InsertUser();
                case CheckPassSingIn ->
                        serverObjectStream.WRITE(DBManager.checkPass(serverObjectStream.READ(), serverObjectStream.READ()) + "");
                case AcceptSignIn -> AcceptSingIn();
                case CreateTweet -> insertTweet(serverObjectStream.readTweet());
                case RefreshTweets -> refreshTweets();
                case SearchUser -> searchUser(serverObjectStream.READ());
                case SearchHashtags -> searchHashtags(serverObjectStream.READ());


                case UpdatePhoneNumber -> DBManager.updatePhoneNumber(serverObjectStream.READ(), serverObjectStream.READ());
                case UpdateEmail -> DBManager.updateEmail(serverObjectStream.READ(), serverObjectStream.READ());
                case UpdatePassword -> DBManager.updatePassword(serverObjectStream.READ(), serverObjectStream.READ());
                case UpdateBio -> DBManager.updateBio(serverObjectStream.READ(), serverObjectStream.READ());
                case UpdateLocation -> DBManager.updateLocation(serverObjectStream.READ(), serverObjectStream.READ());
                case UpdateWebAddress -> DBManager.updateWebAddress(serverObjectStream.READ(), serverObjectStream.READ());
                case UpdateAvatar ->
                        DBManager.updateAvatarOrHeader(serverObjectStream.READ(), serverObjectStream.READ(), "avatar");
                case UpdateHeader ->
                        DBManager.updateAvatarOrHeader(serverObjectStream.READ(), serverObjectStream.READ(), "header");
                case LOGIUT -> DBManager.updateSecretKeyAndJWT(serverObjectStream.READ(), null, null);
            }
        }
    }


    public void AcceptSingIn() {
        String secertyKey;
        do secertyKey = Authentication.JWT.generateSecurityKey();
        while (DBManager.checkSecurityKay(secertyKey));
        userName = serverObjectStream.READ();
        try {
            DBManager.updateSecretKeyAndJWT(userName, secertyKey, Authentication.JWT.generateJWT(userName, secertyKey));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public void InsertUser() {
        String[] in = serverObjectStream.READ().split("//");
        DBManager.insertUserToDB(in[0], in[1], in[2], in[3], in[4], in[5], in[6], in[7],
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
    }

    public static boolean checkUserName(String userName) {
        return DBManager.checkUserName(userName);
    }

    public static boolean checkPass(String pass) {
        boolean length = false, upperCase = false, lowerCase = false;
        for (int i = 0; i < pass.length(); i++) {
            char ch = pass.charAt(i);
            if (Character.isUpperCase(ch)) upperCase = true;
            if (Character.isLowerCase(ch)) lowerCase = true;
            if (upperCase & lowerCase) break;
        }
        if (pass.length() >= 8) length = true;
        return length & upperCase & length;
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
        String[] n = phoneNumber.split("");
        for (int i = 0; i < phoneNumber.length(); i++)
            try {
                int integer = Integer.parseInt(n[i]);
                if (!(integer >= 0 && integer <= 9)) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        if (phoneNumber.length() != 11) {
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(email).matches()) return true;
        return false;
    }

    public void phoneNumber(String phoneNumber) {
        if (!checkPhoneNumber(phoneNumber)) {
            serverObjectStream.WRITE("invalid");
            return;
        }
        if (DBManager.checkPhoneNumber(phoneNumber)) {
            serverObjectStream.WRITE("repeat");
            return;
        }
        serverObjectStream.WRITE("true");
    }

    public void email(String email) {
        if (!isValidEmail(email)) {
            serverObjectStream.WRITE("invalid");
            return;
        }
        if (DBManager.checkEmail(email)) {
            serverObjectStream.WRITE("repeat");
            return;
        }
        serverObjectStream.WRITE("true");
    }

    //-----------------------------------------------------------------------------------------
    // Tweet things:
    private String[] getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String formattedDate = dateFormatter.format(date);
        System.out.println(formattedDate);
        String[] splittedDate = formattedDate.split(" ");
        return splittedDate;
    }

    private String extractHashtags(String message) {
        List<String> hashtags = new ArrayList<>();
        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            hashtags.add(matcher.group());
        }
        String temp = "";
        for (String sa : hashtags) {
            temp = temp.concat(sa + " ");
        }

        return temp;
    }

    public void insertTweet(Tweet tweet) {
        String text = tweet.getText();
        String[] timeAndDate = getCurrentTime();
        String hashtags = extractHashtags(text);
        DBManager.insertTweetToDB(text, null, null, -1, timeAndDate[0], timeAndDate[1], userName, "tweet", hashtags);
    }
    //-----------------------------------------------------------------------------------------
    // Methods related to refresh tweets:

    /**
     * This method get date and time of a tweet which were sent before and saved in db
     * and then calculate its difference with current time in minute scale and after that
     * return proper data for "long minutesDiff" and "String timeDifference" fields .
     */
    private ArrayList<Object> calTimeDifference(String date, String time) {
        String input = date.concat(" " + time);

        // Get current date and time
        LocalDateTime date_time_obj2 = LocalDateTime.now();

        // Convert date string to LocalDateTime object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        LocalDateTime date_time_obj1 = LocalDateTime.parse(input, formatter);

        // Calculate time difference
        Duration duration = Duration.between(date_time_obj1, date_time_obj2);
        long minutes_diff = duration.toMinutes();
        ArrayList<Object> times = new ArrayList<>();

        if (minutes_diff <= 59) {
            times.add("" + minutes_diff + " Minutes ago");
            times.add(minutes_diff);
            return times;
        } else if (minutes_diff <= 1416) {
            times.add("" + duration.toHours() + " Hours ago");
            times.add(minutes_diff);
            return times;
        } else {
            times.add(date);
            times.add(minutes_diff);
            return times;
        }
    }

    /**
     * Set 2 fields of every tweet object:
     * "String timeDifference" and "long minutesDiff" first on is what we want to show in GUI and
     * second one is a field which is a criterion for sorting tweets by the time they were sent
     */
    private ArrayList<Tweet> setTimeDifference(ArrayList<Tweet> allTweets) {
        ArrayList<Tweet> temp = new ArrayList<>();
        for (Tweet tweet : allTweets) {
            ArrayList<Object> times = calTimeDifference(tweet.getSendingDate(), tweet.getSendingTime());
            String timeDifference = (String) times.get(0);
            tweet.setTimeDifference(timeDifference);

            long minutes_diff = (long) times.get(1);
            tweet.setMinutesDiff(minutes_diff);
            temp.add(tweet);
        }
        return temp;
    }

    public void refreshTweets() {
        ArrayList<Tweet> allTweets = DBManager.getAllTweets();
        allTweets = setTimeDifference(allTweets);
        Comparator<Tweet> timeComparator = Comparator.comparingLong(Tweet::getMinutesDiff);
        allTweets.sort(timeComparator); // this is list of sorted tweets and is ready to show in timeline
        serverObjectStream.writeTweetsList(allTweets);
    }

    //-----------------------------------------------------------------------------------------
    // Search things:
    public void searchUser(String input) {
        ArrayList<User> users = DBManager.searchUser(input);
        serverObjectStream.writeUsersList(users);
    }

    public void searchHashtags(String hashtag) {
        ArrayList<Tweet> foundTweets = DBManager.searchHashtags(hashtag);
        serverObjectStream.writeTweetsList(foundTweets);
    }
}