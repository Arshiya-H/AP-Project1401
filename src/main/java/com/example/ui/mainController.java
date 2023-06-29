package com.example.ui;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class mainController implements Initializable {


    @FXML
    private Circle CircleAvatar;

    @FXML
    private ImageView Header;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CircleAvatar.setStroke(Color.BLACK);
        Image image = new Image("/Screenshot 2023-06-28 at 4.36.37 PM.png",false);
        CircleAvatar.setFill(new ImagePattern(image));
        CircleAvatar.setEffect(new DropShadow(5,Color.BLACK));


    }
}
