package com.example.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class searchController implements Initializable {

    @FXML
    private VBox BoxTweet;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchBox;

    @FXML
    void searchUser(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scroll.setBackground(Background.EMPTY);


    }
}
