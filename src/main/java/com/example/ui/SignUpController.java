package com.example.ui;

import UserApplicationSrarter.UserController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static UserApplicationSrarter.ORDER.InsertUser;
import static com.example.ui.HelloApplication.stream;

public class SignUpController implements Initializable {

    private String[] CountryList = {"Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua & Deps", "Argentina",
            "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
            "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina",
            "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Central African Rep", "Chad", "Chile", "China", "Colombia",
            "Comoros", "Congo", "Congo {Democratic Rep}", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark"
            , "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea"
            , "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana"
            , "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland",
            "India", "Indonesia", "Iran", "Iraq", "Ireland {Republic}", "Israel", "Italy", "Ivory Coast", "Jamaica", "Japan", "Jordan"
            , "Kazakhstan", "Kenya", "Kiribati", "Korea North", "Korea South", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia"
            , "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg"
            , "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania"
            , "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique",
            "Myanmar, {Burma}", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Norway"
            , "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal"
            , "Qatar", "Romania", "Russian Federation", "Rwanda", "St Kitts & Nevis", "St Lucia", "Saint Vincent & the Grenadines", "Samoa"
            , "San Marino", "Sao Tome & Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia"
            , "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland"
            , "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga", "Trinidad & Tobago"
            , "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States"
            , "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"};

    @FXML
    private TextField birthdate;

    @FXML
    private ChoiceBox<String> country;

    @FXML
    private TextField email;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastname;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField password;

    @FXML
    private TextField repeatPassword;

    @FXML
    private TextField userName;

    @FXML
    private GridPane gridPane;

    @FXML
    private Parent fxml;
    private final static   Duration start_finish= Duration.seconds(0.25);


    @FXML
    void CreateSignUp(ActionEvent event) throws IOException {
        String awnser = UserController.singUp(stream, userName.getText(), phoneNumber.getText(), email.getText(), password.getText(), repeatPassword.getText());
        if (!awnser.equals("true")) {
            animationMassage(awnser, false);
            return;
        }
        if (firstName.getText().equals("")) {
            animationMassage("First Name must not be empty", false);
            return;
        }
        if (lastname.getText().equals("")) {
            animationMassage("Last Name must not be empty", false);
            return;
        }
        if (country.getValue().equals("CHOOSE A COUNTRY")) {
            animationMassage("you must choose a country", false);
            return;
        }
        if (birthdate.getText().equals("")) {
            animationMassage("Birth Date must not be empty", false);
            return;
        }
        stream.WRITE(InsertUser + "");
        stream.WRITE(userName.getText() + "//" + firstName.getText() + "//" + lastname.getText() + "//" + email.getText() + "//" + phoneNumber.getText() + "//" + password.getText() + "//" + country.getValue() + "//" + birthdate.getText());

        animationMassage("Thank you!\nYour account has been successfully complete.", true);

    }

    public void animationMassage(String text, boolean result) {
        FXMLLoader massage = new FXMLLoader(startController.class.getResource("massageAcceptRefuse.fxml"));
        try {
            fxml = massage.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        massageAcceptRefuseController controller = massage.getController();
        controller.setInformation(text, result);
        gridPane.setTranslateX(gridPane.getPrefWidth() * -0.125);
        gridPane.setOpacity(0);
        gridPane.getChildren().add(fxml);

        Timeline start = new Timeline(
                new KeyFrame(start_finish, new KeyValue(gridPane.translateXProperty(), 0)),
                new KeyFrame(start_finish, new KeyValue(gridPane.opacityProperty(), 1))
        );
        Timeline freeze = new Timeline(new KeyFrame(Duration.seconds(0.75)));
        Timeline finish = new Timeline(
                new KeyFrame(start_finish, new KeyValue(gridPane.translateXProperty(), gridPane.getPrefWidth() * 0.125)),
                new KeyFrame(start_finish, new KeyValue(gridPane.opacityProperty(), 0))
        );
        start.play();
        start.setOnFinished((a) -> freeze.play());
        freeze.setOnFinished((a) -> finish.play());
        finish.setOnFinished((a) -> {
            gridPane.getChildren().remove(fxml);
            new Timeline(
                    new KeyFrame(Duration.millis(1), new KeyValue(gridPane.translateXProperty(),
                            gridPane.getPrefWidth() * 2))).play();
        });
        gridPane.setTranslateX(480);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane.setTranslateX(-480);
        country.setValue("CHOOSE A COUNTRY");
        country.getItems().addAll(CountryList);

    }


}
