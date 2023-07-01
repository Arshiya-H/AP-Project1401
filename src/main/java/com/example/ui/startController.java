package com.example.ui;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class startController implements Initializable {

    private static final Duration DURATION = Duration.seconds(0.8);

    @FXML
    private Pane vBox;
    @FXML
    private VBox vBoxAux;
    @FXML
    private Pane signInPane;
    @FXML
    private Pane signUpPane;


    @FXML
    private Parent fxml;

    ParallelTransition parallelTransition;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rectangle ret = new Rectangle(0, 0, 0, 0);
        ret.widthProperty().bind(vBox.prefWidthProperty());
        ret.heightProperty().bind(vBox.prefHeightProperty());
        vBox.setClip(ret);

        signInPane.setOpacity(0);
        TranslateTransition tVbox = new TranslateTransition(Duration.seconds(1), vBox);
        TranslateTransition tVboxAux = new TranslateTransition(Duration.seconds(1), vBoxAux);
        FadeTransition fVbox = new FadeTransition(DURATION, vBox);

        TranslateTransition TsInPane = new TranslateTransition(Duration.seconds(1), signInPane);
        FadeTransition FsInPane = new FadeTransition(Duration.seconds(1), signInPane);
        TranslateTransition TsUpPane = new TranslateTransition(Duration.seconds(1), signUpPane);
        FadeTransition FsUpPane = new FadeTransition(Duration.seconds(1), signUpPane);

        parallelTransition = new ParallelTransition(fVbox, tVbox, tVboxAux, TsInPane, TsUpPane, FsInPane, FsUpPane);

        parallelTransition.setInterpolator(Interpolator.EASE_BOTH);

        TsUpPane.setToY(signUpPane.getLayoutY() * -0.1);
        FsUpPane.setToValue(0.5);
        fVbox.setToValue(0);
        tVbox.setToX(vBox.getLayoutX() * 6.4);
        tVboxAux.setToX(vBox.getLayoutX() * 6.4);

        parallelTransition.play();
//        parallelTransition.setOnFinished((e) -> {

        FsUpPane.setToValue(1);
        TsUpPane.setToY(signUpPane.getLayoutY() * -0.2);
        tVbox.setToX(vBox.getLayoutX() * 12.8);
        tVboxAux.setToX(vBox.getLayoutX() * 12.8);
        fVbox.setToValue(1);
        parallelTransition.play();

        parallelTransition.setOnFinished((ex) -> {
            parallelTransition.getChildren().removeAll();
        });
        animateChangingLayout("signIn.fxml", false);
//        });
    }

    private void animateChangingLayout(String name, boolean toLeft) {
        try {
            fxml = FXMLLoader.load(getClass().getResource(name));
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        if (vBox.getChildren().size() != 1) {
            vBox.getChildren().clear();
            fxml.setTranslateY(50);
            fxml.setOpacity(0);
            vBox.getChildren().add(fxml);

            new Timeline(
                    new KeyFrame(DURATION, new KeyValue(fxml.translateYProperty(), 0)),
                    new KeyFrame(DURATION, new KeyValue(fxml.opacityProperty(), 1))
            ).play();
        } else {
            Node oldView = vBox.getChildren().get(0);
            fxml.setTranslateX((toLeft ? 1 : -1) * vBox.getPrefWidth());
            vBox.getChildren().add(fxml);

            Timeline timeline = new Timeline(
                    new KeyFrame(DURATION, new KeyValue(oldView.translateXProperty(), (toLeft ? -1 : 1) * vBox.getPrefWidth())),
                    new KeyFrame(DURATION, new KeyValue(fxml.translateXProperty(), 0))
            );
            timeline.setOnFinished((a) -> {
                vBox.getChildren().remove(oldView);
            });
            timeline.play();
        }
    }

    @FXML
    public void open_signIn(ActionEvent event) {
        signIn();

    }

    public void signIn() {
        TranslateTransition tVbox = new TranslateTransition(DURATION, vBox);
        TranslateTransition tVboxAux = new TranslateTransition(DURATION, vBoxAux);
        FadeTransition fVbox = new FadeTransition(DURATION, vBox);

        TranslateTransition TsInPane = new TranslateTransition(DURATION, signInPane);
        FadeTransition FsInPane = new FadeTransition(DURATION, signInPane);
        TranslateTransition TsUpPane = new TranslateTransition(DURATION, signUpPane);
        FadeTransition FsUpPane = new FadeTransition(DURATION, signUpPane);

        parallelTransition = new ParallelTransition(fVbox, tVbox, tVboxAux, TsInPane, TsUpPane, FsInPane, FsUpPane);

        parallelTransition.setInterpolator(Interpolator.EASE_BOTH);

        TsInPane.setToY(signInPane.getLayoutY() * 0.1);
        FsInPane.setToValue(0.5);
        TsUpPane.setToY(signUpPane.getLayoutY() * -0.1);//
        FsUpPane.setToValue(0.5);
        fVbox.setToValue(0);
        tVbox.setToX(vBox.getLayoutX() * 6.4);
        tVboxAux.setToX(vBox.getLayoutX() * 6.4);

        parallelTransition.play();
//        parallelTransition.setOnFinished((e) -> {

        FsUpPane.setToValue(1);
        TsInPane.setToY(signInPane.getLayoutY() * -0.2);
        FsInPane.setToValue(0);
        TsUpPane.setToY(signUpPane.getLayoutY() * -0.2);//
        tVbox.setToX(vBox.getLayoutX() * 12.8);
        tVboxAux.setToX(vBox.getLayoutX() * 12.8);
        fVbox.setToValue(1);
        parallelTransition.play();
        parallelTransition.setOnFinished((ex) -> {
            parallelTransition.getChildren().removeAll();
        });
        animateChangingLayout("signIn.fxml", false);
//        });
    }

    @FXML
    public void open_signUp(ActionEvent event) {
        TranslateTransition tVbox = new TranslateTransition(DURATION, vBox);
        TranslateTransition tVboxAux = new TranslateTransition(DURATION, vBoxAux);
        FadeTransition fVbox = new FadeTransition(DURATION, vBox);
        TranslateTransition TsInPane = new TranslateTransition(DURATION, signInPane);
        FadeTransition FsInPane = new FadeTransition(DURATION, signInPane);
        TranslateTransition TsUpPane = new TranslateTransition(DURATION, signUpPane);
        FadeTransition FsUpPane = new FadeTransition(DURATION, signUpPane);

        parallelTransition = new ParallelTransition(fVbox, tVbox, tVboxAux, TsInPane, TsUpPane, FsInPane, FsUpPane);

        parallelTransition.setInterpolator(Interpolator.EASE_BOTH);

        TsUpPane.setToY(signUpPane.getLayoutY() * 0.05);//
        FsUpPane.setToValue(0.5);
        TsInPane.setToY(signInPane.getLayoutY() * 0.2);
        FsInPane.setToValue(0.5);
        fVbox.setToValue(0);
        tVbox.setToX(vBox.getLayoutX() * 6.4);
        tVboxAux.setToX(vBox.getLayoutX() * 6.4);

        parallelTransition.play();

//        parallelTransition.setOnFinished((e) -> {

        TsUpPane.setToY(signUpPane.getLayoutY() * 0.1);//
        FsUpPane.setToValue(0);
        TsInPane.setToY(signInPane.getLayoutY() * 0.4);
        FsInPane.setToValue(1);
        tVbox.setToX(0);
        tVboxAux.setToX(0);
        fVbox.setToValue(1);
        parallelTransition.play();
        parallelTransition.setOnFinished((ex) -> {
            parallelTransition.getChildren().removeAll();
        });
        animateChangingLayout("signUp.fxml", true);

//        });
    }
}