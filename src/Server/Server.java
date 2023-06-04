package Server;

import DataBase.DBManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket ServerSocket;

    public Server(ServerSocket ServerSocket) {
        this.ServerSocket = ServerSocket;
    }

    public static void main(String[] args) throws IOException {
        DBManager.creatUsersTable();
        ServerSocket ServerSocket = new ServerSocket(2000);
        Server Server = new Server(ServerSocket);
        try {
            while (true) {
                Socket socket = ServerSocket.accept();
                Thread thread = new Thread((Runnable) new ConnectionApp(socket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
