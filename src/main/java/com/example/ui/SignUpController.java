package com.example.ui;

import UserApplicationSrarter.UserController;
import inheritance.ObjectStream;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Delayed;

import static UserApplicationSrarter.ORDER.InsertUser;
import static com.example.ui.HelloApplication.stream;


public class SignUpController implements Initializable {

    @FXML
    private TextField BirthDate;

    @FXML
    private ChoiceBox<String> Country;

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
    private TextField Email;

    @FXML
    private ImageView Image;
    @FXML
    private Label Label;
    @FXML
    private TextField LastName;
    @FXML
    private TextField FirstName;

    @FXML
    private TextField Password;
    @FXML
    private TextField RepeatPassword;
    @FXML
    private TextField PhoneNumber;
    @FXML
    private TextField UserName;

    private Stage stage;

    @FXML
    void BackToStartApp(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StartApp.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void CreateSignUp(ActionEvent event) throws IOException {
        Label.setText("");
        String awnser = UserController.singUp(stream, UserName.getText(), PhoneNumber.getText(), Email.getText(), Password.getText(), RepeatPassword.getText());
        if (!awnser.equals("true")) {
            Label.setText(awnser);
            return;
        }
        if (FirstName.getText().equals("")) {
            Label.setText("First Name must not be empty");
            return;
        }
        if (LastName.getText().equals("")) {
            Label.setText("Last Name must not be empty");
            return;
        }
        if (Country.getValue().equals("CHOOSE A COUNTRY")) {
            Label.setText("you must choose a country");
            return;
        }
        if (BirthDate.getText().equals("")) {
            Label.setText("Birth Date must not be empty");
            return;
        }
        ObjectStream.WRITE(InsertUser + "");
        ObjectStream.WRITE(UserName.getText() + "//" + FirstName.getText() + "//" + LastName.getText() + "//" + Email.getText() + "//" + PhoneNumber.getText() + "//" + Password.getText() + "//" + Country.getValue() + "//" + BirthDate.getText());


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StartApp.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Country.getItems().addAll(CountryList);
        Country.setValue("CHOOSE A COUNTRY");
    }
}