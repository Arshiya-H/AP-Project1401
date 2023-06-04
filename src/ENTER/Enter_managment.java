package ENTER;

import Client.User;
import DataBase.DBConnection;
import org.jooq.DSLContext;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Enter_managment {

    public static void singUp() {
        DBConnection dbConnection = new DBConnection();
        DSLContext DB = dbConnection.getDB();

        Scanner scan = new Scanner(System.in);
        System.out.println("enter a user name :");
        String username = scan.nextLine();
        /////// check username unique .

        System.out.println("enter your first name :");
        String firstname = scan.nextLine();

        System.out.println("enter your last name :");
        String lastname = scan.nextLine();

        System.out.println("choose email(1) or phone(2) or both(3) :");
        String choicePhoneOrEmail = scan.nextLine();

        String phone = null, email = null;

        if (choicePhoneOrEmail.equals("2") || choicePhoneOrEmail.equals("3")) {
            System.out.println("enter your phone number :");
            do {
                if (phone != null) System.out.println("enter a correct phone number :");
                phone = scan.nextLine();
            } while (!checkPhoneNumber(phone));
            ///check side server or duplicate
        }
        if (choicePhoneOrEmail.equals("1") || choicePhoneOrEmail.equals("3")) {
            System.out.println("enter your email :");
            do {
                if (email != null) System.out.println("enter a correct email :");
                email = scan.nextLine();
            } while (!isValidEmail(email));
            ///check side server or duplicate
        }

        String pass = null;
        String auxPass;

        do {

            if (pass != null) System.out.println("invalid password :");
            else System.out.println("write a password :");
            pass = scan.nextLine();

            System.out.println("repeat your password :");
            auxPass = scan.nextLine();

        } while (pass.equals(auxPass) && !checkPass(pass));

        System.out.println("enter a country (if you want can see list of country by enter show) :");
        String country = scan.nextLine();
        if (country.equals("show")) {
            show();
            country = scan.nextLine();
        }
        System.out.println("enter your birthdate by this format YYYY/MM/DD :");
        String birthDate = scan.nextLine();


        new User(username, firstname, lastname, pass, country, birthDate, choicePhoneOrEmail, phone, email);
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
        for (int i = 0; i < phoneNumber.length(); i++) {
            try {
                int integer = Integer.parseInt(n[i]);
                if (!(integer >= 0 && integer <= 9)) return false;
            } catch (Exception e) {
                return false;
            }
        }
        return phoneNumber.length() == 11;
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }
}

