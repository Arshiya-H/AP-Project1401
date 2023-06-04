package UserApplicationSrarter;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class StartApplication {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 2000);
            while (true) {
                System.out.println("\tChoose an option : \n\t\t1 - sign up\n\t\t2 - sign in");

                String sign = scan.nextLine();
                if (sign.equals("1")) UserController.singUp();
                if (sign.equals("2")) UserController.signIn();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}