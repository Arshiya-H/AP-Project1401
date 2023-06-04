package server;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionApp {

    Socket socket;
    private String JWT;

    public ConnectionApp(Socket socket) {
        this.socket = socket;
    }

}
