package UserApplicationSrarter;

import Authentication.JWT;
import DataBase.DBManager;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserController {

    public static void singUp() {
        String username = null, phone = null, email = null, pass = null, auxPass;
        Scanner scan = new Scanner(System.in);

        System.out.println("enter a user name :");
        do {
            if (username != null) System.out.println("the username has existed already, choose another :");
            username = scan.nextLine();
        }
        while (DBManager.checkUserName(username));

        System.out.println("enter your first name :");
        String firstname = scan.nextLine();

        System.out.println("enter your last name :");
        String lastname = scan.nextLine();

        System.out.println("choose email(1) or phone(2) or both(3) :");
        String choicePhoneOrEmail = scan.nextLine();

        if (choicePhoneOrEmail.equals("2") || choicePhoneOrEmail.equals("3")) {
            System.out.println("enter your phone number :");
            do phone = scan.nextLine();
            while (!checkPhoneNumber(phone) || DBManager.checkPhoneNumber(phone));

        }
        if (choicePhoneOrEmail.equals("1") || choicePhoneOrEmail.equals("3")) {
            System.out.println("enter your email :");
            do email = scan.nextLine();
            while (!isValidEmail(email) || DBManager.checkEmail(email));
        }

        do {

            if (pass != null) System.out.println("invalid password :");
            else System.out.println("write a password :");
            pass = scan.nextLine();

            System.out.println("repeat your password :");
            auxPass = scan.nextLine();

        } while (!pass.equals(auxPass) || !checkPass(pass));

        System.out.println("enter a country (if you want can see list of country by enter show) :");
        String country = scan.nextLine();
        if (country.equals("show")) {
            show();
            country = scan.nextLine();
        }
        System.out.println("enter your birthdate by this format YYYY/MM/DD :");
        String birthDate = scan.nextLine();

        DBManager.insertUserToDB(username, firstname, lastname, email, phone, pass, country, birthDate,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
    }

    public static void signIn() {
        Scanner scan = new Scanner(System.in);
        String username = null, pass = null, secertyKey;
        System.out.println("enter your username :");

        do {
            if (username != null) System.out.println("the username has not existed, choose another :");
            username = scan.nextLine();
        }
        while (!DBManager.checkUserName(username));

        System.out.println("enter your password :");
        do {
            if (pass != null) System.out.println("invalid pass, try again :");
            pass = scan.nextLine();
        }
        while (!DBManager.checkPass(username, pass));


        do secertyKey = JWT.generateSecurityKey();
        while (DBManager.checkSecurityKay(secertyKey));

        try {
            DBManager.updateSecretKeyAndJWT(username, secertyKey, JWT.generateJWT(username, secertyKey));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException e) {
            e.printStackTrace();
        }

        ///////////////

    }

    public static void show() {
        System.out.println("\tlist of country :\niran");
    }

    public static boolean checkPass(String pass) {
        boolean length = false, upperCase = false, lowerCase = false;
        for (int i = 0; i < pass.length(); i++) {
            char ch = pass.charAt(i);
            if (Character.isUpperCase(ch)) upperCase = true;
            if (Character.isLowerCase(ch)) lowerCase = true;
            if (upperCase & lowerCase) break;
        }
        if (pass.length() >= 8) length = true;
        return length & upperCase & length;
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
        String[] n = phoneNumber.split("");
        for (int i = 0; i < phoneNumber.length(); i++)
            try {
                int integer = Integer.parseInt(n[i]);
                if (!(integer >= 0 && integer <= 9)) {
                    System.out.println("invalid phone number, write again :");
                    return false;
                }
            } catch (Exception e) {
                System.out.println("invalid phone number, write again :");
                return false;
            }
        if (phoneNumber.length() != 11) {
            System.out.println("invalid phone number, write again :");
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(email).matches()) return true;
        System.out.println("your email is invalid, try again :");
        return false;
    }
}

