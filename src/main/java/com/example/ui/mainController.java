package com.example.ui;

import Client.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainController implements Initializable {

    private String oldPage = "";
    @FXML
    private Circle CircleAvatar;

    @FXML
    private Button LogoutBTN;

    @FXML
    private VBox VboxScreen;

    @FXML
    private Label followerNumber;

    @FXML
    private Label followingNumber;

    @FXML
    private Rectangle rectangleHeader;

    @FXML
    private Label tweetNumber;

    @FXML
    private Label userName;

    @FXML
    void DirectBTN(ActionEvent event) {

    }

    @FXML
    void EditProfileBTN(ActionEvent event) {
        load("editProfile.fxml");
    }

    @FXML
    void HashtagStatisticsBTN(ActionEvent event) {
        load("hashtagStatics.fxml");
    }

    @FXML
    void followerNumberBTN(ActionEvent event) {

    }

    @FXML
    void followingNumberBTN(ActionEvent event) {

    }

    @FXML
    void homeBTN(ActionEvent event) {
        load("home.fxml");
    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void newTwettBTN(ActionEvent event) {
        load("sendtweet.fxml");
    }

    @FXML
    void search(ActionEvent event) {
        load("search.fxml");
    }

    @FXML
    void tweetNumberBTN(ActionEvent event) {

    }

    public void showAccount(String name, User user) {
        if (name.equals(oldPage)) return;
        oldPage = name;
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource(name));
            Parent root = fxml.load();
            ((userAccountController) fxml.getController()).srtInformation(user);
            if (VboxScreen.getChildren().size() != 0) {
                Node old = VboxScreen.getChildren().get(0);
                VboxScreen.getChildren().remove(old);
            }
            VboxScreen.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rectangleHeader.setArcWidth(100);
        rectangleHeader.setArcHeight(100);
        Image header = new Image("/Screenshot 2023-06-28 at 4.36.37 PM.png", false);
        rectangleHeader.setFill(new ImagePattern(header));
        rectangleHeader.setEffect(new GaussianBlur(2));

        CircleAvatar.setStroke(Color.BLACK);
        Image avatar = new Image("/pngwing.com.png", false);
        CircleAvatar.setFill(new ImagePattern(avatar));
        CircleAvatar.setEffect(new DropShadow(5, Color.BLACK));
    }

    public void load(String name) {
        if (name.equals(oldPage)) return;
        oldPage = name;
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource(name));
            Parent root = fxml.load();
            if (VboxScreen.getChildren().size() != 0) {
                Node old = VboxScreen.getChildren().get(0);
                VboxScreen.getChildren().remove(old);
            }
            VboxScreen.getChildren().add(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
