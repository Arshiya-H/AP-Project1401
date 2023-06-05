package UserApplicationSrarter;

import DataBase.DBManager;
import inheritance.ObjectStream;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class StartApplication extends ObjectStream {

    public StartApplication(Socket socket) {
        super(socket);
    }


    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 2000);
            StartApplication startApplication = new StartApplication(socket);
//            startApplication.getAnswer();
            while (true) {
                System.out.println("\tChoose an option : \n\t\t1 - sign up\n\t\t2 - sign in\n\t\t3 - logout");

                String sign = scan.nextLine();
                switch (sign) {
                    case "1" -> {
                        startApplication.bufferedWriter.write("1" + "\n");
                        startApplication.bufferedWriter.flush();
                        UserController.singUp(bufferedWriter, bufferedReader);
                    }
                    case "2" -> {
                        startApplication.bufferedWriter.write("2" + "\n");
                        startApplication.bufferedWriter.flush();
                        UserController.signIn(bufferedWriter, bufferedReader);

                    }
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