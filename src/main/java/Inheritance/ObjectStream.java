package Inheritance;

import Tweet.Tweet;

import java.io.*;
import java.net.Socket;

public class ObjectStream {

    protected String username;
    protected BufferedWriter bufferedWriter;
    protected BufferedReader bufferedReader;

    private  ObjectOutputStream outputStream;
    private  ObjectInputStream inputStream;

    public ObjectStream(Socket socket) {
        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public BufferedWriter getBufferedWriter() {
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

    public void WRITE(String text) {
        try {
            bufferedWriter.write(text + "\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * These methods read and write tweets object as an object
     * */
    public void writeTweet(Tweet tweet){
        try {
            outputStream.writeObject(tweet);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Returns Tweet object
     * */
    public Tweet readTweet(){
        try {
            return (Tweet) inputStream.readObject();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
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
