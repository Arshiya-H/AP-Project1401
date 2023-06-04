package server;

import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

    private ServerSocket serverSocket;

    public server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000);
        server server = new server(serverSocket);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread((Runnable) new ConnectionApp(socket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
