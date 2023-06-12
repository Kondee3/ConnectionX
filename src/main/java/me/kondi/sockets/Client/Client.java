package me.kondi.sockets.Client;

import me.kondi.sockets.DataStream;
import me.kondi.sockets.Message.Message;
import me.kondi.sockets.Message.MessageSender;
import me.kondi.sockets.Message.MessageType;
import me.kondi.sockets.User.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class Client {

    private Socket s;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private final JTextArea chatField;
    private final JTextField messageField;

    private final User user;


    public Client(JTextArea chatField, JTextField messageField, String login, String password) {

        this.chatField = chatField;

        this.messageField = messageField;

        this.user = new User(login, password);

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

            sendUserDataForServer(user);


                runMessageReceiver();




        } catch (IOException ex) {
            System.out.println(ex);
        }


    }

    public void sendUserDataForServer(User user) {
        try {
            Message loginMessage = new Message(MessageType.LOGIN, MessageSender.CLIENT, user);
            out.writeObject(loginMessage);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    private void runMessageReceiver() {
        new Thread(() -> {
            while (true) {
                try {
                    Message message = (Message) in.readObject();

                    switch (message.getMessageType()){
                        case MESSAGE -> {
                            if (message.getMessageSender() == MessageSender.SERVER) {
                                chatField.append("Server: " + message.getText() + "\n");
                            } else {
                                chatField.append(message.getUser().getClientLogin() + ": " + message.getText() + "\n");
                            }
                        }
                    }
                } catch (IOException i) {
                    System.out.println(i);

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();
    }

    public void sendMessage() {
        try {
            Message message = new Message(MessageType.MESSAGE, MessageSender.CLIENT, user, messageField.getText());
            out.writeObject(message);
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
