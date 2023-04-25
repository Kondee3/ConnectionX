package me.kondi.sockets.Client;

import me.kondi.sockets.DataStream;
import org.json.JSONObject;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class Client {

    private Socket s;
    private DataInputStream in;
    private DataOutputStream out;

    private JTextArea chatField;
    private JTextField messageField;

    private String login;

    public Client(JTextArea chatField, JTextField messageField, String login) {

        this.chatField = chatField;

        this.messageField = messageField;

        this.login = login;

        setupClient();

    }


    public void setupClient() {
        try {

            String ip = "localhost";

            int port = 25566;

            s = new Socket(ip, port);

            System.out.println("Client connected");

            DataStream dataStream = new DataStream(s);

            in = dataStream.getDataInputStream();

            out = dataStream.getDataOutputStream();

            sendUserDataForServer(login);

            runMessageReceiver();






        } catch (IOException ex) {
            System.out.println(ex);
        }


    }

    public void sendUserDataForServer(String login){
        try {

            JSONObject data = new JSONObject();
            data.put("type", "login");
            data.put("login", login);
            out.writeUTF(data.toString());

        } catch (IOException i) {
            System.out.println(i);
        }
    }

    private void runMessageReceiver() {
        new Thread(() -> {
            try {
                while(true){

                    String line = "Host: " + in.readUTF() + "\n";
                    chatField.append(line);
                    System.out.println(line);
                }


            } catch (IOException i) {
                System.out.println(i);
            }
        }).start();
    }

    public void sendMessage() {

        try {
            JSONObject data = new JSONObject();
            data.put("type", "message");
            data.put("text", messageField.getText());
            out.writeUTF(data.toString());

        } catch (IOException i) {
            System.out.println(i);
        }
    }


}
