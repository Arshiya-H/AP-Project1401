package com.example.ui;

import inheritance.ObjectStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class HelloApplication extends Application {
    protected static ObjectStream stream;
    static Socket socket;

    @Override
    public void start(Stage stage) throws IOException {
        socket = new Socket("localhost", 2000);
        stream = new ObjectStream(socket);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StartApp.fxml"));
        Scene scene = new Scene( fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}