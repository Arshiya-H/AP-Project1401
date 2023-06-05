package inheritance;

import java.io.*;
import java.net.Socket;

public class ObjectStream {
    protected static Socket socket;
    protected String username;
    protected static BufferedWriter bufferedWriter;
    protected static BufferedReader bufferedReader;

    public ObjectStream(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
