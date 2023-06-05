package Server;

import DataBase.DBManager;
import inheritance.ObjectStream;

import java.io.IOException;
import java.net.Socket;
import java.util.regex.Pattern;

public class ConnectionApp extends ObjectStream implements Runnable {

    private String JWT;

    public ConnectionApp(Socket socket) {
        super(socket);
    }


    @Override
    public void run() {
        String order;
        while (socket.isConnected()) {
            try {
                order = bufferedReader.readLine();
                switch (order) {    //  1-signup    2-sign in   3-log out
                    case "1" -> signUp();
                    case "2" -> signIn();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void signUp() {
        boolean check = false;
        try {
            String[] order;
            while (socket.isConnected() && !check) {
                order = bufferedReader.readLine().split("//");
                switch (order[0]) {
                    case "1" -> {
                        bufferedWriter.write(checkUserName(order[1]) ? "true\n" : "false\n");
                        bufferedWriter.flush();
                    }
                    case "2" -> {
                        phoneNumber(order[1]);
                    }
                    case "3" -> {
                        email(order[1]);
                    }
                    case "4" -> {
                        bufferedWriter.write(checkPass(order[1]) ? "true\n" : "false\n");
                        bufferedWriter.flush();
                    }
                    case "5" -> check = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signIn() {
        boolean check = false;
        try {
            while (socket.isConnected() && !check) {
                String read = bufferedReader.readLine();
                String[] order = read.split("//");
                switch (order[0]) {
                    case "1" -> {
                        bufferedWriter.write(checkUserName(order[1]) ? "true\n" : "false\n");
                        bufferedWriter.flush();
                    }
                    case "2" -> {
                        check = DBManager.checkPass(order[1], order[2]);
                        bufferedWriter.write(check ? "true\n" : "false\n");
                        bufferedWriter.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        afterSignIn();
    }

    public void afterSignIn() {
        try {
            String order;
            while (socket.isConnected()) {
                order = bufferedReader.readLine();
                switch (order) {
                    case "1" -> {
                        return;
                    }
                    case "2" -> editProfile();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editProfile() {
        boolean check = false;
        try {
            String[] order;
            while (socket.isConnected() && !check) {
                order = bufferedReader.readLine().split("//");
                switch (order[0]) {
                    case "2" -> {
                        phoneNumber(order[1]);
                        String[] aux = bufferedReader.readLine().split("//");
                        boolean answer = checkPhoneNumber(aux[2]);
                        bufferedWriter.write(answer ? "true\n" : "false\n");
                        bufferedWriter.flush();
                        if (answer) DBManager.updatePhoneNumber(order[1], order[2]);
                    }
                    case "3" -> DBManager.updateEmail(order[1], order[2]);
                    case "4" -> DBManager.updatePassword(order[1], order[2]);
                    case "5" -> DBManager.updateBio(order[1], order[2]);
                    case "6" -> DBManager.updateLocation(order[1], order[2]);
                    case "7" -> DBManager.updateWebAddress(order[1], order[2]);
                    case "8" -> DBManager.updateAvatarOrHeader(order[2], order[1], "avatar");
                    case "9" -> DBManager.updateAvatarOrHeader(order[2], order[1], "header");
                    case "0" -> {
                        return;
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean checkUserName(String userName) {
        return DBManager.checkUserName(userName);
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
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        if (phoneNumber.length() != 11) {
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(email).matches()) return !DBManager.checkEmail(email);
        return false;
    }

    public static void phoneNumber(String phoneNumber) {
        try {
            if (!checkPhoneNumber(phoneNumber)) {
                bufferedWriter.write("invalid\n");
                bufferedWriter.flush();
                return;
            }
            if (DBManager.checkPhoneNumber(phoneNumber)) {
                bufferedWriter.write("repeat\n");
                bufferedWriter.flush();
                return;
            }
            bufferedWriter.write("true\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void email(String email) {
        try {
            if (!isValidEmail(email)) {
                bufferedWriter.write("invalid\n");
                bufferedWriter.flush();
                return;
            }
            if (DBManager.checkEmail(email)) {
                bufferedWriter.write("repeat\n");
                bufferedWriter.flush();
                return;
            }
            bufferedWriter.write("true\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}