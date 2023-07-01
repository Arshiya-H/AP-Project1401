package UserApplicationSrarter;

import Inheritance.ObjectStream;

import java.util.Scanner;

import static UserApplicationSrarter.ORDER.*;

public class UserController {

    public static String singUp(ObjectStream stream, String username, String phone
            , String email, String pass, String repeatPass) {
        if (phone.equals("") && email.equals("")) return "You should at least write one of email or phone number";

        stream.WRITE((String.valueOf(CheckUserName)));
        stream.WRITE((username));
        if (!stream.READ().equals("false")) return "the username has existed already, choose another";

        if (!phone.equals("")) {
            String phoneCheck = getPhoneNumber(stream, phone);
            if (!phoneCheck.equals("true")) return phoneCheck;
        }

        if (!email.equals("")) {
            String emailCheck = getEmail(stream, email);
            if (!emailCheck.equals("true")) return emailCheck;
        }

        String passCheck = getPass(stream, pass, repeatPass);
        if (!passCheck.equals("true")) return passCheck;

        return "true";
    }


    public static String signIn(ObjectStream stream, String UserName, String Password) {
        stream.WRITE((String.valueOf(CheckUserName)));
        stream.WRITE((UserName));
        if (stream.READ().equals("false")) return "the username has not existed";

        stream.WRITE(String.valueOf(CheckPassSingIn));
        stream.WRITE(UserName);
        stream.WRITE(Password);
        if (stream.READ().equals("false")) return "invalid Password, try again";
        stream.WRITE(String.valueOf(AcceptSignIn));
        stream.WRITE(UserName);

        return "true";
    }

    public static String getPhoneNumber(ObjectStream stream, String phone) {
        stream.WRITE(String.valueOf(PhoneNumber));
        stream.WRITE((phone));
        String answer = stream.READ();
        if (answer.equals("invalid")) return "phone number is invalid, try again";
        if (answer.equals("repeat")) return "phone number is available, choose another";
        return "true";
    }

    public static String getEmail(ObjectStream stream, String email) {
        stream.WRITE(String.valueOf(Email));
        stream.WRITE((email));
        String answer = stream.READ();
        if (answer.equals("invalid")) return "email is invalid, try again";
        if (answer.equals("repeat")) return "email is available, choose another";
        return "true";
    }

    public static String getPass(ObjectStream stream, String pass, String repeatPass) {
        boolean validPass = true;
        stream.WRITE(String.valueOf(CheckPass));
        stream.WRITE((pass));
        validPass = stream.READ().equals("true");
        if (!validPass) return "invalid password";

        if (validPass) if (!pass.equals(repeatPass)) return "repeated password is incorrect, try again";
        return "true";
    }
}

