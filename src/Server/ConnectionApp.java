package Server;

import java.net.Socket;

public class ConnectionApp implements Runnable {

    Socket socket;
    private String JWT;

    public ConnectionApp(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {

    }
}
