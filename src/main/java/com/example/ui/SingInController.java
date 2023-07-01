package com.example.ui;

import UserApplicationSrarter.UserController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.desktop.SystemSleepEvent;
import java.beans.Visibility;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.ui.HelloApplication.stream;

public class SingInController implements Initializable {

    @FXML
    private TextField Password;

    @FXML
    private TextField UserName;
    @FXML
    private GridPane gridPane;

    @FXML
    private Parent fxml;
    private final static Duration start_finish = Duration.seconds(0.25);

    @FXML
    void SignIn(ActionEvent event) {
        String awnser = UserController.signIn(stream, UserName.getText(), Password.getText());
        if (awnser.equals("true")) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1.5),
                            e -> animationMassage("accept", true)));
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
                stage1.show();
                stage.close();
            });
        } else animationMassage(awnser, false);
    }

    public void animationMassage(String text, boolean result) {
        FXMLLoader massage = new FXMLLoader(startController.class.getResource("massageAcceptRefuse.fxml"));
        try {
            fxml = massage.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        massageAcceptRefuseController controller = massage.getController();
        controller.setInformation(text, result);
        gridPane.setTranslateX(gridPane.getPrefWidth() * 0.125);
        gridPane.setOpacity(0);
        gridPane.getChildren().add(fxml);

        Timeline start = new Timeline(
                new KeyFrame(start_finish, new KeyValue(gridPane.translateXProperty(), 0)),
                new KeyFrame(start_finish, new KeyValue(gridPane.opacityProperty(), 1))
        );
        Timeline freeze = new Timeline(new KeyFrame(Duration.seconds(0.75)));
        Timeline finish = new Timeline(
                new KeyFrame(start_finish, new KeyValue(gridPane.translateXProperty(), -gridPane.getPrefWidth() * 0.125)),
                new KeyFrame(start_finish, new KeyValue(gridPane.opacityProperty(), 0))
        );
        start.play();
        start.setOnFinished((a) -> freeze.play());
        freeze.setOnFinished((a) -> finish.play());
        finish.setOnFinished((a) -> {
            gridPane.getChildren().remove(fxml);
            new Timeline(
                    new KeyFrame(Duration.millis(1), new KeyValue(gridPane.translateXProperty(),
                            -gridPane.getPrefWidth() * 2))).play();
        });
        gridPane.setTranslateX(-480);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane.setTranslateX(480);
    }
}