package com.example.ui;

import UserApplicationSrarter.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static UserApplicationSrarter.ORDER.InsertUser;
import static com.example.ui.HelloApplication.stream;

public class SignUpController extends SingInController implements Initializable {

    protected String[] CountryList = {"Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua & Deps", "Argentina",
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
    protected TextField birthdate;

    @FXML
    protected ChoiceBox<String> country;

    @FXML
    protected TextField email;

    @FXML
    protected TextField firstName;

    @FXML
    protected TextField lastname;

    @FXML
    protected TextField phoneNumber;

    @FXML
    protected TextField repeatPassword;

    @FXML
    private Parent fxml;

    public boolean check() {
        String awnser = UserController.singUp(stream, UserName.getText(), phoneNumber.getText(), email.getText(), Password.getText(), repeatPassword.getText());
        if (!awnser.equals("true")) {
            animationMassage(awnser, false, false);
            return false;
        }
        if (firstName.getText().equals("")) {
            animationMassage("First Name must not be empty", false, false);
            return false;
        }
        if (lastname.getText().equals("")) {
            animationMassage("Last Name must not be empty", false, false);
            return false;
        }
        if (country.getValue().equals("CHOOSE A COUNTRY")) {
            animationMassage("you must choose a country", false, false);
            return false;
        }
        if (birthdate.getText().equals("")) {
            animationMassage("Birth Date must not be empty", false, false);
            return false;
        }
        return true;
    }

    @FXML
    void CreateSignUp(ActionEvent event) throws IOException {
        if (!check()) return;
        stream.WRITE(InsertUser + "");
        stream.WRITE(UserName.getText() + "//" + firstName.getText() + "//" + lastname.getText() + "//" + email.getText() + "//" + phoneNumber.getText() + "//" + Password.getText() + "//" + country.getValue() + "//" + birthdate.getText());

        animationMassage("Thank you!\nYour account has been successfully complete.", true, false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane.setTranslateX(-480);
        country.setValue("CHOOSE A COUNTRY");
        country.getItems().addAll(CountryList);
    }
}
