package me.kondi.sockets.Server;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private ServerSocket ss;

    private List<Socket> sockets = new ArrayList<>();
    private DataInputStream in;
    private DataOutputStream out;
    private JTextArea chatField;
    private JTextField messageField;

    public Server(JTextArea chatField, JTextField messageField) {
        this.chatField = chatField;
        this.messageField = messageField;
    }

    public void setupHost() throws IOException {
        System.out.println("Server has started");
        ss = new ServerSocket(25566);
        
        System.out.println("Server is waiting for client");
        Thread connection = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = ss.accept();
                   sockets.add(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        connection.start();

        System.out.println("Client connected");



        String line = "";
        // reads message from client until "Over" is sent


        Thread receiver = new Thread(new Runnable() {
            @Override
            public void run() {
                for (Socket socket : sockets) {


                    String line = "";

                    try {
                        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                        line = in.readUTF();
                        chatField.append("Client: " + line + "\n");

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
    for (Socket socket : sockets)
        try {
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(messageField.getText());


        } catch (IOException i) {
            System.out.println(i);
        }
    }


}
