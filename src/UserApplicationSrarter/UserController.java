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

        if (choicePhoneOrEmail.equals("2") || choicePhoneOrEmail.equals("3"))
            phone = getPhoneNumber(bufferedWriter, bufferedReader);

        if (choicePhoneOrEmail.equals("1") || choicePhoneOrEmail.equals("3"))
            email = getEmail(bufferedWriter, bufferedReader);

        pass = getPass(bufferedWriter, bufferedReader);

        bufferedWriter.write("5\n");
        bufferedWriter.flush();

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

        afterSignIn(username, bufferedWriter, bufferedReader);
    }

    public static void afterSignIn(String username, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        Scanner scan = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("\t1 - log out\n\t2 - edit profile");
                String order = scan.nextLine();
                switch (order) {
                    case "1" -> {
                        bufferedWriter.write("1\n");
                        bufferedWriter.flush();
                        return;
                    }
                    case "2" -> {
                        bufferedWriter.write("2\n");
                        bufferedWriter.flush();
                        editProfile(username, bufferedWriter, bufferedReader);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editProfile(String username, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\t1 - enter phone number\n\t2 - enter email\n\t3 - change password\n\t" +
                    "4 - write a bio\n\t5 - set location\n\t6 - write your web address\n\t7 - set avatar\n\t" +
                    "8 - set header\n\t9 - back");
            String order = scan.nextLine();
            try {
                switch (order) {
                    case "1" -> {
                        bufferedWriter.write("1//" + username + "//" + getPhoneNumber(bufferedWriter, bufferedReader) + "\n");
                        bufferedWriter.flush();

                    }
                    case "2" -> {
                        bufferedWriter.write("2//" + username + "//" + getEmail(bufferedWriter, bufferedReader) + "\n");
                        bufferedWriter.flush();
                    }
                    case "3" -> {
                        bufferedWriter.write("3//" + username + "//" + getPass(bufferedWriter, bufferedReader) + "\n");
                        bufferedWriter.flush();
                    }
                    case "4" -> {
                        System.out.println("enter your bio (at most character is 160)");
                        String bio = null;
                        do {
                            if (bio != null) System.out.println("your bio is too long,try again by at most 160 character :");
                            bio = scan.nextLine();
                        } while (!(bio.length() <= 160));
                        bufferedWriter.write("4//" + username + "//" + bio + "\n");
                        bufferedWriter.flush();
                    }
                    case "5" -> {
                        System.out.println("write your location :");
                        bufferedWriter.write("5//" + username + "//" + scan.nextLine() + "\n");
                        bufferedWriter.flush();
                    }
                    case "6" -> {
                        System.out.println("write your web address :");
                        bufferedWriter.write("6//" + username + "//" + scan.nextLine() + "\n");
                        bufferedWriter.flush();
                    }
                    case "7" -> {
                        System.out.println("Give path of your avatar file like this(c:\\directory\\file_name.jpg) ");
                        bufferedWriter.write("7//" + username + "//" + scan.nextLine() + "\n");
                        bufferedWriter.flush();
                    }
                    case "8" -> {
                        System.out.println("Give path of your header file like this(c:\\directory\\file_name.jpg) ");
                        bufferedWriter.write("7//" + username + "//" + scan.nextLine() + "\n");
                        bufferedWriter.flush();
                    }
                    case "9" -> {
                        return;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPhoneNumber(BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        Scanner scan = new Scanner(System.in);
        String phone = null;
        System.out.println("enter your phone number :");
        String answer;
        try {
            do {
                phone = scan.nextLine();
                bufferedWriter.write(("2//" + phone + "\n"));
                bufferedWriter.flush();
                answer = bufferedReader.readLine();
                if (answer.equals("invalid")) System.out.println("phone number is invalid, try again :");
                if (answer.equals("repeat")) System.out.println("phone number is available, choose another : ");
            } while (!answer.equals("true"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return phone;
    }

    public static String getEmail(BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        System.out.println("enter your email :");
        Scanner scan = new Scanner(System.in);
        String answer, email = null;
        try {
            do {
                email = scan.nextLine();
                bufferedWriter.write(("3//" + email + "\n"));
                bufferedWriter.flush();
                answer = bufferedReader.readLine();
                if (answer.equals("invalid")) System.out.println("email is invalid, try again :");
                if (answer.equals("repeat")) System.out.println("email is available, choose another : ");
            } while (!answer.equals("true"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return email;
    }

    public static String getPass(BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        boolean checkSame = true, validPass = true;
        Scanner scan = new Scanner(System.in);
        String pass = null, auxPass;
        System.out.println("write a password :");
        try {
            do {
                pass = scan.nextLine();
                System.out.println("repeat your password :");
                auxPass = scan.nextLine();
                bufferedWriter.write(("4//" + pass + "\n"));
                bufferedWriter.flush();

                validPass = bufferedReader.readLine().equals("true");
                if (!validPass) System.out.println("invalid password :");

                if (validPass) {
                    if (!pass.equals(auxPass)) {
                        System.out.println("repeated password is incorrect, try again :");
                        checkSame = false;
                    } else checkSame = true;
                }
            } while (!validPass || !checkSame);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pass;
    }

}

