package Server;

import DataBase.DBManager;
import UserApplicationSrarter.ORDER;
import Inheritance.ObjectStream;

import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class ConnectionApp implements Runnable {

    private String JWT;
    private static String userName;
    private ObjectStream serverObjectStream;
    private  Socket socket;

    public ConnectionApp(ObjectStream serverObjectStream, Socket socket) {
        this.serverObjectStream = serverObjectStream;
        this.socket = socket;
    }




    @Override
    public void run() {
        while (socket.isConnected()) {
            switch (ORDER.valueOf(serverObjectStream.READ())) {
                case CheckUserName -> serverObjectStream.WRITE(checkUserName(serverObjectStream.READ()) + "");
                case PhoneNumber -> phoneNumber(serverObjectStream.READ());
                case Email -> email(serverObjectStream.READ());
                case CheckPass -> serverObjectStream.WRITE(checkPass(serverObjectStream.READ()) + "");
                case InsertUser -> InsertUser();
                case CheckPassSingIn -> serverObjectStream.WRITE(DBManager.checkPass(serverObjectStream.READ(), serverObjectStream.READ()) + "");
                case AcceptSignIn -> AcceptSingIn();


                case UpdatePhoneNumber -> DBManager.updatePhoneNumber(serverObjectStream.READ(), serverObjectStream.READ());
                case UpdateEmail -> DBManager.updateEmail(serverObjectStream.READ(), serverObjectStream.READ());
                case UpdatePassword -> DBManager.updatePassword(serverObjectStream.READ(), serverObjectStream.READ());
                case UpdateBio -> DBManager.updateBio(serverObjectStream.READ(), serverObjectStream.READ());
                case UpdateLocation -> DBManager.updateLocation(serverObjectStream.READ(), serverObjectStream.READ());
                case UpdateWebAddress -> DBManager.updateWebAddress(serverObjectStream.READ(), serverObjectStream.READ());
                case UpdateAvatar -> DBManager.updateAvatarOrHeader(serverObjectStream.READ(), serverObjectStream.READ(), "avatar");
                case UpdateHeader -> DBManager.updateAvatarOrHeader(serverObjectStream.READ(), serverObjectStream.READ(), "header");
                case LOGIUT -> DBManager.updateSecretKeyAndJWT(serverObjectStream.READ(), null, null);
            }
        }
    }


    public void AcceptSingIn() {
        String secertyKey;
        do secertyKey = Authentication.JWT.generateSecurityKey();
        while (DBManager.checkSecurityKay(secertyKey));
        userName = serverObjectStream.READ();
        try {
            DBManager.updateSecretKeyAndJWT(userName, secertyKey, Authentication.JWT.generateJWT(userName, secertyKey));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public void InsertUser() {
        String[] in = serverObjectStream.READ().split("//");
        DBManager.insertUserToDB(in[0], in[1], in[2], in[3], in[4], in[5], in[6], in[7],
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
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
        if (pattern.matcher(email).matches()) return true;
        return false;
    }

    public void phoneNumber(String phoneNumber) {
        if (!checkPhoneNumber(phoneNumber)) {
            serverObjectStream.WRITE("invalid");
            return;
        }
        if (DBManager.checkPhoneNumber(phoneNumber)) {
            serverObjectStream.WRITE("repeat");
            return;
        }
        serverObjectStream.WRITE("true");
    }

    public void email(String email) {
        if (!isValidEmail(email)) {
            serverObjectStream.WRITE("invalid");
            return;
        }
        if (DBManager.checkEmail(email)) {
            serverObjectStream.WRITE("repeat");
            return;
        }
        serverObjectStream.WRITE("true");
    }
}