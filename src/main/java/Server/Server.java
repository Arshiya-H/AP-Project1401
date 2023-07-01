package Server;

import DataBase.DBManager;
import Inheritance.ObjectStream;

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
        DBManager.creatFollowingTable();
        ServerSocket ServerSocket = new ServerSocket(2000);
        Server Server = new Server(ServerSocket);
        try {
            while (true) {
                Socket socket = ServerSocket.accept();
                ObjectStream serverObjectStream = new ObjectStream(socket);
                ConnectionApp app = new ConnectionApp(serverObjectStream,socket);
                Thread thread = new Thread(app);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
