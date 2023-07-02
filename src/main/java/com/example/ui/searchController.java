package com.example.ui;

import Client.User;
import Tweet.Tweet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.ui.HelloApplication.stream;

import Tweet.TweetController;

public class searchController implements Initializable {

    @FXML
    private VBox BoxUser;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchBox;

    @FXML
    private Parent fxml;


    @FXML
    void searchUser(ActionEvent event) {
        BoxUser.getChildren().clear();
        String[] m = searchBox.getText().split("#");
        String input = searchBox.getText();
        // search fo hashtags:
        if (m.length == 2) {
            ArrayList<Tweet> foundTweets = TweetController.searchHashtags(stream, input);
            if (foundTweets == null) {
                searchBox.setText("No result!");
                return;
            }
            for (Tweet tweet : foundTweets) {
                try {
                    FXMLLoader n = new FXMLLoader(getClass().getResource("tweetPage.fxml"));
                    fxml = n.load();
                    tweetPageController tweetPageController = n.getController();
                    tweetPageController.getInformation(tweet.getText(), tweet.getTimeDifference(), tweet.getTweetOwnerUsername(), tweet.getTweetOwnerFullName());
                    BoxUser.getChildren().add(fxml);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        // search for users:
        if (m.length == 1) {
            ArrayList<User> foundUser = TweetController.searchUser(stream, input);
            if (foundUser == null) {
                searchBox.setText("No result!");
                return;
            }
            ArrayList<User> users = TweetController.searchUser(stream, input);
            for (User user : users) {
                try {
                    FXMLLoader n = new FXMLLoader(getClass().getResource("userPage.fxml"));
                    fxml = n.load();
                    ((userPageController) n.getController()).setInformation(user);
                    userPageController userPageController = n.getController();
                    userPageController.setInformation(user.getFirstName() + " " + user.getLastName(), user.getUserName());
                    BoxUser.getChildren().add(fxml);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scroll.setBackground(Background.EMPTY);
    }
}
