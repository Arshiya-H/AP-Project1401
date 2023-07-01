package com.example.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class editProfileController extends SignUpController implements Initializable {

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
        if (!check()) ;
    }

    @FXML
    void headerBTN(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        country.getItems().addAll(CountryList);
    }
}
