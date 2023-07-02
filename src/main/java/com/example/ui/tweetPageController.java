package com.example.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class tweetPageController implements Initializable {

    @FXML
    private Circle circleIProfile;
    @FXML
    private Button like;
    @FXML
    private Label likesRetweet;
    @FXML
    private Button qute;
    @FXML
    private Button retweet;


    @FXML
    private Label text;
    @FXML
    private Label date;
    @FXML
    private Label fullName;
    @FXML
    private Label userName;

    @FXML
    void like(ActionEvent event) {

    }

    @FXML
    void qute(ActionEvent event) {

    }

    @FXML
    void retweet(ActionEvent event) {

    }

    public void getInformation(String text, String date, String fullName, String userName) {
        this.text.setText(text);
        this.date.setText(date);
        this.fullName.setText(fullName);
        this.userName.setText(userName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
