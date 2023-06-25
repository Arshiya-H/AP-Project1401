package UserApplicationSrarter;

import DataBase.DBManager;
import inheritance.ObjectStream;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static UserApplicationSrarter.ORDER.*;

public class UserController {

    public static String singUp(ObjectStream stream, String username, String phone
            , String email, String pass, String repeatPass) {
        if (phone.equals("") && email.equals("")) return "You should at least write one of email or phone number";
//        String username = null, phone = null, email = null, pass = null;
//        Scanner scan = new Scanner(System.in);

//        System.out.println("enter a user name :");
//        do {
//            if (username != null) System.out.println("the username has existed already, choose another :");
//            username = scan.nextLine();

        ObjectStream.WRITE((String.valueOf(CheckUserName)));
        ObjectStream.WRITE((username));
//        } while (!stream.READ().equals("false"));
        if (!stream.READ().equals("false")) return "the username has existed already, choose another";
//        System.out.println("enter your first name :");
//        String firstname = scan.nextLine();
//
//        System.out.println("enter your last name :");
//        String lastname = scan.nextLine();
//
//        System.out.println("choose email(1) or phone(2) or both(3) :");
//        String choicePhoneOrEmail = scan.nextLine();
//
//        if (choicePhoneOrEmail.equals("2") || choicePhoneOrEmail.equals("3"))
        if (!phone.equals("")) {
            String phoneCheck = getPhoneNumber(stream, phone);
            if (!phoneCheck.equals("true")) return phoneCheck;
        }

//        if (choicePhoneOrEmail.equals("1") || choicePhoneOrEmail.equals("3"))
        if (!email.equals("")) {
            String emailCheck = getEmail(stream, email);
            if (!emailCheck.equals("true")) return emailCheck;
        }

        String passCheck = getPass(stream, pass, repeatPass);
        if (!passCheck.equals("true")) return passCheck;
//        System.out.println("enter a country (if you want can see list of country by enter show) :");
//        String country = scan.nextLine();
//        if (country.equals("show")) {
//            show();
//            country = scan.nextLine();
//        }
//        System.out.println("enter your birthdate by this format YYYY/MM/DD :");
//        String birthDate = scan.nextLine();

        return "true";

    }

    public static void show() {
        System.out.println("\tlist of country :\niran");
    }

    public static String signIn(ObjectStream stream, String UserName, String Password) {
//        Scanner scan = new Scanner(System.in);
//        String username = null, pass = null;
//        System.out.println("enter your username :");
//        do {
//        if (username != null) System.out.println("the username has not existed, choose another :");
//        username = scan.nextLine();
        ObjectStream.WRITE((String.valueOf(CheckUserName)));
        ObjectStream.WRITE((UserName));
//        } while (stream.READ().equals("false"));
        if (stream.READ().equals("false")) return "the username has not existed";

//        System.out.println("enter your password :");
//        do {
//            if (pass != null) System.out.println("invalid pass, try again :");
//            pass = scan.nextLine();
        ObjectStream.WRITE(String.valueOf(CheckPassSingIn));
        ObjectStream.WRITE(UserName);
        ObjectStream.WRITE(Password);
//        }while (stream.READ().equals("false"));
        if (stream.READ().equals("false")) return "invalid Password, try again";
        ObjectStream.WRITE(String.valueOf(AcceptSignIn));
        ObjectStream.WRITE(UserName);

//        afterSignIn(UserName, stream);
        return "true";
    }

    public static void afterSignIn(String username, ObjectStream stream) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\t1 - log out\n\t2 - edit profile");
            String order = scan.nextLine();
            switch (order) {
                case "1" -> {
                    stream.WRITE(LOGIUT + "");
                    stream.WRITE(username);
                    return;
                }
//                case "2" -> editProfile(username, stream);
            }
        }
    }

//    public static void editProfile(String username, ObjectStream stream) {
//        Scanner scan = new Scanner(System.in);
//        while (true) {
//            System.out.println("""
//                    \t1 - enter phone number
//                    \t2 - enter email
//                    \t3 - change password
//                    \t4 - write a bio
//                    \t5 - set location
//                    \t6 - write your web address
//                    \t7 - set avatar
//                    \t8 - set header
//                    \t9 - back""");
//
//            switch (scan.nextLine()) {
//                case "1" -> {
//                    String PhoneNumber = getPhoneNumber(stream);
//                    ObjectStream.WRITE(String.valueOf(UpdatePhoneNumber));
//                    ObjectStream.WRITE(PhoneNumber);
//                    ObjectStream.WRITE(username);
//                }
//                case "2" -> {
//                    String Email = getEmail(stream);
//                    ObjectStream.WRITE(String.valueOf(UpdateEmail));
//                    ObjectStream.WRITE(Email);
//                    ObjectStream.WRITE(username);
//                }
//                case "3" -> {
//                    String Pass = getPass(stream);
//                    ObjectStream.WRITE(String.valueOf(UpdatePassword));
//                    ObjectStream.WRITE(Pass);
//                    ObjectStream.WRITE(username);
//                }
//                case "4" -> {
//                    System.out.println("enter your bio (at most character is 160)");
//                    String bio = null;
//                    do {
//                        if (bio != null) System.out.println("your bio is too long,try again by at most 160 character :");
//                        bio = scan.nextLine();
//                    } while (!(bio.length() <= 160));
//                    ObjectStream.WRITE(String.valueOf(UpdateBio));
//                    ObjectStream.WRITE(bio);
//                    ObjectStream.WRITE(username);
//                }
//                case "5" -> {
//                    System.out.println("write your location :");
//                    ObjectStream.WRITE(String.valueOf(UpdateLocation));
//                    ObjectStream.WRITE(scan.nextLine());
//                    ObjectStream.WRITE(username);
//                }
//                case "6" -> {
//                    System.out.println("write your web address :");
//                    ObjectStream.WRITE(String.valueOf(UpdateWebAddress));
//                    ObjectStream.WRITE(scan.nextLine());
//                    ObjectStream.WRITE(username);
//                }
//                case "7" -> {
//                    System.out.println("Give path of your avatar file like this(c:\\directory\\file_name.jpg) ");
//                    ObjectStream.WRITE(String.valueOf(UpdateAvatar));
//                    ObjectStream.WRITE(scan.nextLine());
//                    ObjectStream.WRITE(username);
//                }
//                case "8" -> {
//                    System.out.println("Give path of your header file like this(c:\\directory\\file_name.jpg) ");
//                    ObjectStream.WRITE(String.valueOf(UpdateHeader));
//                    ObjectStream.WRITE(scan.nextLine());
//                    ObjectStream.WRITE(username);
//                }
//                case "9" -> {
//                    return;
//                }
//            }
//        }
//    }

    public static String getPhoneNumber(ObjectStream stream, String phone) {
//        Scanner scan = new Scanner(System.in);
//        String phone = null;
//        System.out.println("enter your phone number :");
//        String answer;
//        do {
//            phone = scan.nextLine();
        ObjectStream.WRITE(String.valueOf(PhoneNumber));
        ObjectStream.WRITE((phone));
        String answer = stream.READ();
        if (answer.equals("invalid")) return "phone number is invalid, try again :";
        if (answer.equals("repeat")) return "phone number is available, choose another : ";
//        } while (!answer.equals("true"));
        return "true";
    }

    public static String getEmail(ObjectStream stream, String email) {
//        System.out.println("enter your email :");
//        Scanner scan = new Scanner(System.in);
//        String answer, email = null;
//        do {
//            email = scan.nextLine();
        ObjectStream.WRITE(String.valueOf(Email));
        ObjectStream.WRITE((email));
        String answer = stream.READ();
        if (answer.equals("invalid")) return "email is invalid, try again :";
        if (answer.equals("repeat")) return "email is available, choose another : ";
//        } while (!answer.equals("true"));
        return "true";
    }

    public static String getPass(ObjectStream stream, String pass, String repeatPass) {
        boolean validPass = true;
//        Scanner scan = new Scanner(System.in);
//        String pass = null, auxPass;
//        System.out.println("write a password :");
//        do {
//            pass = scan.nextLine();
//            System.out.println("repeat your password :");
//            auxPass = scan.nextLine();
        ObjectStream.WRITE(String.valueOf(CheckPass));
        ObjectStream.WRITE((pass));
        validPass = stream.READ().equals("true");
        if (!validPass) return "invalid password :";

        if (validPass) if (!pass.equals(repeatPass)) return "repeated password is incorrect, try again :";

//    } while(!validPass ||!checkSame);
        return "true";
    }
}

