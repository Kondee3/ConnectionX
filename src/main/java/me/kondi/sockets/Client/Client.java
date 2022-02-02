package me.kondi.sockets.Client;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket s;
    private DataInputStream inHost;
    private DataOutputStream out;
    private JTextArea chatField;
    private JTextField messageField;

    public Client(JTextArea chatField, JTextField messageField) {
        this.chatField = chatField;
        this.messageField = messageField;
    }


    public void setupClient() throws IOException {
        String ip = "localhost";
        int port = 25566;
        s = new Socket(ip, port);
        System.out.println("Client connected");

        out = new DataOutputStream(s.getOutputStream());
        inHost = new DataInputStream(new BufferedInputStream(s.getInputStream()));


        Thread receiver = new Thread(new Runnable() {
            @Override
            public void run() {
                String line = "";
                while (!line.equals("Over")) {
                    try {
                        line = inHost.readUTF();
                        chatField.append("Host: " + line + "\n");
                        System.out.println(line);

                    } catch (IOException i) {
                        System.out.println(i);
                    }
                }
            }
        });
        receiver.start();


    }

    public void sendMessage() {

        try {
            out.writeUTF(messageField.getText());
        } catch (IOException i) {
            System.out.println(i);
        }
    }


}
