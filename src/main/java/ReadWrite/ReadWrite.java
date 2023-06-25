package ReadWrite;

import java.io.*;
import java.net.Socket;

public class ReadWrite {
    public static void Write(String answer, Socket socket) {
        try {
            BufferedWriter buf = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            buf.write(answer);
            buf.flush();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String Read(Socket socket) {
        String Read = null;
        try {
            BufferedReader buf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Read = buf.readLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Read;
    }
}
