package com.example.ui;

import Client.User;
import Tweet.TweetController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static UserApplicationSrarter.ORDER.EditProfile;
import static UserApplicationSrarter.ORDER.GetUserName;
import static com.example.ui.HelloApplication.stream;


public class editProfileController extends animation implements Initializable {

    @FXML
    private TextArea Bio;

    @FXML
    private Circle avatar;

    @FXML
    private Rectangle header;

    @FXML
    private TextField location;
    @FXML
    private TextField webAddress;

    @FXML
    void avatarBTN(ActionEvent event) {
    }

    @FXML
    void changeInformation(ActionEvent event) {
        gridPane.setTranslateX(gridPane.getPrefWidth());
        if (Bio.getText().length() > 160) {
            animationMassage("Bio should be less than 160 characters", false, false);
            return;
        }
        stream.WRITE(EditProfile + "");
        stream.WRITE_OBJECT(location.getText());
        stream.WRITE_OBJECT(Bio.getText());
        stream.WRITE_OBJECT(webAddress.getText());
//        stream.WRITE_OBJECT(avatar);
//        stream.WRITE_OBJECT(header);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.5),
                        e -> animationMassage("Profile complete", true, false)));
        timeline.setCycleCount(2);
        timeline.play();
        timeline.setOnFinished((a) -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.setMinHeight(680);
            stage1.setMinWidth(980);
            stage1.setMaxWidth(980);
            stage1.show();
            stage.close();
        });
    }

    @FXML
    void headerBTN(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stream.WRITE(GetUserName + "");
        User user = TweetController.searchUser(stream, stream.READ()).get(0);
        Bio.setText(user.getBio());
        location.setText(user.getLocation());
        webAddress.setText(user.getWebAddress());
        gridPane.setTranslateX(700);
    }
}
