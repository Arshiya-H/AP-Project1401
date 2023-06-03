package ENTER;

import java.util.Scanner;

public class Enter_managment {

    public static void singUp() {

        Scanner scan = new Scanner(System.in);
        System.out.println("enter a UNIQUE user name :");
        String username = scan.nextLine();
        /////// check username unique .

        System.out.println("enter your first name :");
        String firstname = scan.nextLine();

        System.out.println("enter your last name :");
        String lastname = scan.nextLine();

        System.out.println("choice BETWEEN email(1) or phone(2) :");
        String choicePhoneOrEmail = scan.nextLine();

        if (choicePhoneOrEmail.equals("1")) System.out.println("enter your email :");
        else System.out.println("enter your phone number :");

        String choice = null;

        if (choicePhoneOrEmail.equals("2")) {
            do {
                if (choice != null) System.out.println("enter a correct phone number :");
                choice = scan.nextLine();
            } while (!checkPhoneNumber(choice));
        } else {
        }


        String pass = null;
        String auxPass;
        do {
            if (pass != null) System.out.println("please choice correct or enter same password :");
            else System.out.println("choice a password :");
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
        for (int i = 0; i < phoneNumber.length(); i++) {
            int n = Integer.parseInt(phoneNumber.indent(i));
            if (!(n >= 0 && n <= 9)) return false;
        }
        return phoneNumber.length() == 11;
    }
}
