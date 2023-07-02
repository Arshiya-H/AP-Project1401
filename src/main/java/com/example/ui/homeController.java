package com.example.ui;

import Tweet.Tweet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Tweet.TweetController;

import static com.example.ui.HelloApplication.stream;

public class homeController implements Initializable {


    @FXML
    private VBox BoxTweet;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Parent fxml;

    public void refresh() {
        ArrayList<Tweet> tweets = TweetController.refreshTweets(stream);
        for (Tweet tweet : tweets) {
            try {
                FXMLLoader n = new FXMLLoader(getClass().getResource("tweetPage.fxml"));
                fxml = n.load();
                tweetPageController tweetPageController = n.getController();
                tweetPageController.getInformation(tweet.getText(), tweet.getTimeDifference(), tweet.getTweetOwnerUsername(), tweet.getTweetOwnerFullName());
                BoxTweet.getChildren().add(fxml);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scroll.setBackground(Background.EMPTY);
        refresh();
    }
}
