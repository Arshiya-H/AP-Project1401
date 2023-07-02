package com.example.ui;

import UserApplicationSrarter.UserController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.ui.HelloApplication.stream;

public class SingInController extends animation implements Initializable {

    @FXML
    protected TextField Password;

    @FXML
    protected TextField UserName;

    @FXML
    void SignIn(ActionEvent event) {
        String awnser = UserController.signIn(stream, UserName.getText(), Password.getText());
        if (awnser.equals("true")) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1.5),
                            e -> animationMassage("accept", true, true)));
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
                ((mainController) fxmlLoader.getController()).load("home.fxml");
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
        } else animationMassage(awnser, false, true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane.setTranslateX(480);
    }
}