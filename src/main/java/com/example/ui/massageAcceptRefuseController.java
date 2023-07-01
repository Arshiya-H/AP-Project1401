package com.example.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class massageAcceptRefuseController {

    @FXML
    private ImageView image;

    @FXML
    private Label massage;

    public void setInformation(String massage, Boolean result) {
        this.massage.setTextFill(result ? Color.GREEN : Color.RED);
        this.massage.setText(massage);
        this.image.setImage(new Image(result ? "success.png" : "refuse.png", false));
    }

}
