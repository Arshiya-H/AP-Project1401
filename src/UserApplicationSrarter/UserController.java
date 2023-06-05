package UserApplicationSrarter;

import Authentication.JWT;
import DataBase.DBManager;
import Server.ConnectionApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class UserController {

    public static void singUp(BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws IOException {
        String username = null, phone = null, email = null, pass = null, auxPass, result = null;
        Scanner scan = new Scanner(System.in);

        System.out.println("enter a user name :");
        try {
            do {
                if (username != null) System.out.println("the username has existed already, choose another :");
                username = scan.nextLine();
                bufferedWriter.write(("1//" + username + "\n"));
                bufferedWriter.flush();
            } while (!bufferedReader.readLine().equals("false"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("enter your first name :");
        String firstname = scan.nextLine();

        System.out.println("enter your last name :");
        String lastname = scan.nextLine();

        System.out.println("choose email(1) or phone(2) or both(3) :");
        String choicePhoneOrEmail = scan.nextLine();

        if (choicePhoneOrEmail.equals("2") || choicePhoneOrEmail.equals("3")) {
            System.out.println("enter your phone number :");
            do {
                if (phone != null) System.out.println("invalid phone number, write again :");
                phone = scan.nextLine();
                bufferedWriter.write(("2//" + phone + "\n"));
                bufferedWriter.flush();
            } while (bufferedReader.readLine().equals("false"));

        }
        if (choicePhoneOrEmail.equals("1") || choicePhoneOrEmail.equals("3")) {
            System.out.println("enter your email :");
            do {
                if (email != null) System.out.println("your email is invalid, try again :");
                email = scan.nextLine();
                bufferedWriter.write(("3//" + email + "\n"));
                bufferedWriter.flush();
            } while (bufferedReader.readLine().equals("false"));
        }

        do {
            if (pass != null) System.out.println("invalid password :");
            else System.out.println("write a password :");
            pass = scan.nextLine();

            System.out.println("repeat your password :");
            auxPass = scan.nextLine();
            bufferedWriter.write(("4//" + pass + "\n"));
            bufferedWriter.flush();
        } while (!pass.equals(auxPass) || bufferedReader.readLine().equals("false"));

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

    public static void show() {
        System.out.println("\tlist of country :\niran");
    }

    public static void signIn(BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        Scanner scan = new Scanner(System.in);
        String username = null, pass = null, secertyKey;
        try {
            System.out.println("enter your username :");
            do {
                if (username != null) System.out.println("the username has not existed, choose another :");
                username = scan.nextLine();
                bufferedWriter.write("1//" + username + "\n");
                bufferedWriter.flush();
            }
            while (bufferedReader.readLine().equals("false"));

            System.out.println("enter your password :");
            do {
                if (pass != null) System.out.println("invalid pass, try again :");
                pass = scan.nextLine();
                bufferedWriter.write("2//" + username + "//" + pass + "\n");
                bufferedWriter.flush();
            }
            while (bufferedReader.readLine().equals("false"));


            do secertyKey = JWT.generateSecurityKey();
            while (DBManager.checkSecurityKay(secertyKey));


            DBManager.updateSecretKeyAndJWT(username, secertyKey, JWT.generateJWT(username, secertyKey));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | IOException e) {
            e.printStackTrace();
        }

        afterSignIn(username);
    }

    public static void afterSignIn(String username) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\t1 - log out\n\t2 - edit profile");
            String order = scan.nextLine();
            switch (order) {
                case "1" -> {

                    return;
                }
                case "2" -> editProfile(username);

            }
        }
    }

    public static void editProfile(String username) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\t1 - enter phone number\n\t2 - enter email\n\t3 - change password\n\t" +
                    "4 - write a bio\n\t5 - set location\n\t6 - write your web address\n\t7 - logout");
            String order = scan.nextLine();
            switch (order) {
                case "1" -> {
                    System.out.println("enter your phone number :");
                    do order = scan.nextLine();
                    while (!ConnectionApp.checkPhoneNumber(order) || DBManager.checkPhoneNumber(order));
                    DBManager.updatePhoneNumber(username, order);
                }
                case "2" -> {
                    System.out.println("enter your email :");
                    do order = scan.nextLine();
                    while (!ConnectionApp.isValidEmail(order) || DBManager.checkEmail(order));
                    DBManager.updateEmail(username, order);
                }
                case "3" -> {
                    String pass = null, auxPass;
                    do {
                        if (pass != null) System.out.println("invalid password :");
                        else System.out.println("write a password :");
                        pass = scan.nextLine();
                        System.out.println("repeat your password :");
                        auxPass = scan.nextLine();
                    } while (!pass.equals(auxPass) || !ConnectionApp.checkPass(pass));
                    DBManager.updatePassword(username, pass);
                }
                case "4" -> {
                    System.out.println("enter your bio (at most character is 160)");
                    String bio = null;
                    do {
                        if (bio != null) System.out.println("your bio is too long,try again by at most 160 character :");
                        bio = scan.nextLine();
                    } while (!(bio.length() <= 160));
                    DBManager.updateBio(username, bio);
                }
                case "5" -> {
                    System.out.println("write your location :");
                    order = scan.nextLine();
                    DBManager.updateLocation(username, order);

                }
                case "6" -> {
                    System.out.println("write your web address :");
                    order = scan.nextLine();
                    DBManager.updateWebAddress(username, order);
                }
                case "7" -> {
                    return;
                }
            }
        }
    }


}

