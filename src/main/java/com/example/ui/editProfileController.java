package com.example.ui;

import Client.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

import static DataBase.DBManager.updateSubjectUser;
import static UserApplicationSrarter.ORDER.EditProfile;
import static com.example.ui.HelloApplication.stream;


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
    private GridPane gridPane;

    @FXML
    void avatarBTN(ActionEvent event) {

    }

    @FXML
    void changeInformation(ActionEvent event) {
        gridPane.setTranslateX(gridPane.getPrefWidth());
        if (Bio.getText().length() > 160) {
            animationMassage("Bio should be less than 160 characters", false, false);
            return;
        }
        stream.WRITE(EditProfile + "");
        stream.WRITE_OBJECT(location.getText());
        stream.WRITE_OBJECT(Bio.getText());
        stream.WRITE_OBJECT(webAddress.getText());
//        stream.WRITE_OBJECT(avatar);
//        stream.WRITE_OBJECT(header);
    }

    @FXML
    void headerBTN(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane.setTranslateX(700);
    }
}
