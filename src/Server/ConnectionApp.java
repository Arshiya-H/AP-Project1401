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
                switch (order) {    //  1-signup    2-sign in   3-logout
                    case "1" -> signUp();
                    case "2" -> signIn();
//                case "3" ->
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void signUp() {
        try {
            String[] order;
            while (socket.isConnected()) {
                order = bufferedReader.readLine().split("//");
                switch (order[0]) {
                    case "1" -> {
                        bufferedWriter.write(checkUserName(order[1]) ? "ture\n" : "false\n");
                        bufferedWriter.flush();
                    }
                    case "2" -> {
                        bufferedWriter.write(checkPhoneNumber(order[1]) ? "ture\n" : "false\n");
                        bufferedWriter.flush();
                    }
                    case "3" -> {
                        bufferedWriter.write(isValidEmail(order[1]) ? "ture\n" : "false\n");
                        bufferedWriter.flush();
                    }
                    case "4" -> {
                        bufferedWriter.write(checkPass(order[1]) ? "ture\n" : "false\n");
                        bufferedWriter.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signIn() {
        try {
            String[] order;
            while (socket.isConnected()) {
                order = bufferedReader.readLine().split("//");
                switch (order[0]) {
                    case "1" -> {
                        bufferedWriter.write(checkUserName(order[1]) ? "ture\n" : "false\n");
                        bufferedWriter.flush();
                    }
                    case "2" -> {
                        bufferedWriter.write(DBManager.checkPass(order[1], order[2]) ? "ture\n" : "false\n");
                        bufferedWriter.flush();
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
        return !DBManager.checkPhoneNumber(phoneNumber);
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(email).matches()) return !DBManager.checkEmail(email);
        return false;
    }
}