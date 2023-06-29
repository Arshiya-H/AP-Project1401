package com.example.ui;

import UserApplicationSrarter.UserController;
import Inheritance.ObjectStream;
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
    private Label lableAnwser;

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
        lableAnwser.setText("");
        String awnser = UserController.singUp(stream, userName.getText(), phoneNumber.getText(), email.getText(), password.getText(), repeatPassword.getText());
        if (!awnser.equals("true")) {
            lableAnwser.setText("! " + awnser + " !");
            return;
        }
        if (firstName.getText().equals("")) {
            lableAnwser.setText("First Name must not be empty");
            return;
        }
        if (lastname.getText().equals("")) {
            lableAnwser.setText("Last Name must not be empty");
            return;
        }
        if (country.getValue().equals("CHOOSE A COUNTRY")) {
            lableAnwser.setText("you must choose a country");
            return;
        }
        if (birthdate.getText().equals("")) {
            lableAnwser.setText("Birth Date must not be empty");
            return;
        }
        stream.WRITE(InsertUser + "");
        stream.WRITE(userName.getText() + "//" + firstName.getText() + "//" + lastname.getText() + "//" + email.getText() + "//" + phoneNumber.getText() + "//" + password.getText() + "//" + country.getValue() + "//" + birthdate.getText());

        lableAnwser.setText("# accept #");


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        country.setValue("CHOOSE A COUNTRY");
        country.getItems().addAll(CountryList);
    }


}
