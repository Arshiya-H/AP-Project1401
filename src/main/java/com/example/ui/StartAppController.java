package com.example.ui;

import UserApplicationSrarter.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.ui.HelloApplication.stream;

public class StartAppController {

    @FXML
    private Label LabelSignIn;

    @FXML
    private TextField Password;

    @FXML
    private ImageView image;


    @FXML
    private TextField UserName;

    @FXML
    void SignIn(ActionEvent event) {
        LabelSignIn.setText("");
        String awnser = UserController.signIn(stream, UserName.getText(), Password.getText());
        if (awnser.equals("true")) ;////        sign in now
        else LabelSignIn.setText(awnser);
    }


    private Stage stage;
//    private Scene scene;

    @FXML
    void SignUp(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("SignUp.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

}