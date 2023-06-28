package Inheritance;

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

    public static Socket getSocket() {
        return socket;
    }

    public static BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public static BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public String READ() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void WRITE(String text) {
        try {
            bufferedWriter.write(text + "\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] READ_SPLIT() {
        try {
            return bufferedReader.readLine().split("//");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
