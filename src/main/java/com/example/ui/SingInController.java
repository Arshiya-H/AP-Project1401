package com.example.ui;

import UserApplicationSrarter.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.example.ui.HelloApplication.stream;

public class SingInController {

    @FXML
    private Label LabelSignIn;

    @FXML
    private TextField Password;

    @FXML
    private TextField UserName;

    @FXML
    void SignIn(ActionEvent event) {
        LabelSignIn.setText("");
        String awnser = UserController.signIn(stream, UserName.getText(), Password.getText());
        if (awnser.equals("true")) LabelSignIn.setText("# " + "accept" + " #");
        else LabelSignIn.setText("! " + awnser + " !");
    }
}