package Server;

import java.net.Socket;

public class ConnectionApp {

    Socket socket;
    private String JWT;

    public ConnectionApp(Socket socket) {
        this.socket = socket;
    }

}
