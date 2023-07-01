package Inheritance;

import Client.User;
import Tweet.Tweet;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ObjectStream {

    protected String username;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public ObjectStream(Socket socket) {
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String READ() {
        try {
            return (String) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void WRITE(String text) {
        try {
            outputStream.writeObject(text);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object READ_OBJECT() {
        try {
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void WRITE_OBJECT(Object object) {
        try {
            outputStream.writeObject(object);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * These methods read and write tweets object as an object
     */
    public void writeTweet(Tweet tweet) {
        try {
            outputStream.writeObject(tweet);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns Tweet object
     */
    public Tweet readTweet() {
        try {
            return (Tweet) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeTweetsList(ArrayList<Tweet> massages) {
        try {
            outputStream.writeObject(massages);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Tweet> readTweetsList() {
        try {
            return (ArrayList<Tweet>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeUsersList(ArrayList<User> users) {
        try {
            outputStream.writeObject(users);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<User> readUsersList() {
        try {
            return (ArrayList<User>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
