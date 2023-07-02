package com.example.ui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;

public class animation {
    @FXML
    protected GridPane gridPane;
    @FXML
    private Parent fxml;
    protected final static Duration start_finish = Duration.seconds(0.25);

    public void animationMassage(String text, boolean result, boolean toleft) {
        FXMLLoader massage = new FXMLLoader(startController.class.getResource("massageAcceptRefuse.fxml"));
        try {
            fxml = massage.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        massageAcceptRefuseController controller = massage.getController();
        controller.setInformation(text, result);
        gridPane.setTranslateX(gridPane.getPrefWidth() * (toleft ? 1 : -1) * 0.125);
        gridPane.setOpacity(0);
        gridPane.getChildren().add(fxml);

        Timeline start = new Timeline(
                new KeyFrame(start_finish, new KeyValue(gridPane.translateXProperty(), 0)),
                new KeyFrame(start_finish, new KeyValue(gridPane.opacityProperty(), 1))
        );
        Timeline freeze = new Timeline(new KeyFrame(Duration.seconds(0.75)));
        Timeline finish = new Timeline(
                new KeyFrame(start_finish, new KeyValue(gridPane.translateXProperty(), -gridPane.getPrefWidth() * (toleft ? 1 : -1) * 0.125)),
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
}
