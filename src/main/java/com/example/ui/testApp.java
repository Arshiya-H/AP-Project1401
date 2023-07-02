package com.example.ui;

import Inheritance.ObjectStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.Socket;

public class testApp extends Application {
    protected static ObjectStream stream;
    protected Socket socket;
    @Override
    public void start(Stage stage) throws IOException {
        socket = new Socket("localhost", 2000);
        stream = new ObjectStream(socket);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hashtagStatics.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMinWidth(980);
        stage.setMinHeight(680);
        //        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Hello!");
        stage.setScene(scene);
//        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}