package UserApplicationSrarter;

import DataBase.DBManager;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class StartApplication {
    public static void main(String[] args) {
        DBManager.creatUsersTable();
        Scanner scan = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 2000);
            System.out.println("Choose an option : \n1 - sign up\n2 - sign in");
            String sign = scan.nextLine();
            if (sign.equals("1")) UserController.singUp();
            //else Enter_managment.       /////////////////      ;    //make sign in.

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
