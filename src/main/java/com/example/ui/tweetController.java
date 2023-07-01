package com.example.ui;

import Tweet.TweetController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.ui.HelloApplication.stream;

public class tweetController {

    @FXML
    private TextField imageAddress;

    @FXML
    private TextArea text;

    @FXML
    void sendTweet(ActionEvent event) {
        TweetController.creatTweet(stream, text.getText());
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
//        Parent root;
//        try {
//            root = fxmlLoader.load();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        Scene scene = new Scene(root);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        Stage stage1 = new Stage();
//        stage1.setScene(scene);
//        stage1.setMinHeight(680);
//        stage1.setMinWidth(980);
//        stage1.setMaxWidth(980);
//        stage1.show();
//        stage.close();
    }
}
