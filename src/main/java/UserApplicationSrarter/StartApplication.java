package UserApplicationSrarter;

import inheritance.ObjectStream;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class StartApplication {

    static ObjectStream stream;
    static Socket socket;

    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);
            socket = new Socket("localhost", 2000);
            stream = new ObjectStream(socket);
//            StartApplication startApplication = new StartApplication(socket);
            while (true) {
                System.out.println("\tChoose an option : \n\t\t1 - sign up\n\t\t2 - sign in\n\t\t3 - exit");
                String sign = scan.nextLine();
                switch (sign) {
//                    case "1" -> UserController.singUp(stream);
//                    case "2" -> UserController.signIn(stream);
                    case "3" -> {
                        return;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}