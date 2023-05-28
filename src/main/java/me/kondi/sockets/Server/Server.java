package me.kondi.sockets.Server;

import me.kondi.sockets.DataStream;
import me.kondi.sockets.Message.Message;
import me.kondi.sockets.Message.MessageSender;
import me.kondi.sockets.Message.MessageType;
import me.kondi.sockets.UserDataStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {

    private ServerSocket ss;

    private HashMap<Socket, DataStream> sockets = new HashMap<>();
    private HashMap<Socket, UserDataStream> userSockets = new HashMap<>();

    public Server() {
    }

    public void setupHost() {
        System.out.println("Server has started");
        try {
            ss = new ServerSocket(25566);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Server is waiting for client");

        runConnectionManager();
        runHandshakeReceiver();
        runMessageReceiver();
        runMessageSender();

    }

    private void runMessageSender() {
        Runnable scan = () -> {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNext())
                    sendMessage(scanner.nextLine());
            }
        };
        scan.run();
    }

    private void runConnectionManager() {
        new Thread(() -> {
            try {
                while (true) {
                    Socket s = ss.accept();
                    if (!sockets.keySet().contains(s)) {
                        sockets.put(s, new DataStream(s));
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void runHandshakeReceiver() {
        new Thread(() -> {
            while (true) {
                for (Map.Entry<Socket, DataStream> socketDataInputStreamEntry : sockets.entrySet()) {
                    try {
                        DataStream dataStream = socketDataInputStreamEntry.getValue();
                        ObjectInputStream data = dataStream.getDataInputStream();
                        Message message = (Message) data.readObject();

                        switch (message.getMessageType()) {
                            case LOGIN:
                                userSockets.put(socketDataInputStreamEntry.getKey(), new UserDataStream(dataStream, message.getUser()));
                                sockets.remove(socketDataInputStreamEntry.getKey());
                                break;
                            case REGISTER:
                                System.out.println(message.getUser().getClientLogin() + ": " + message.getUser().getPassword() + "\n");
                                break;
                        }


                    } catch (IOException ex) {
                        System.out.println(ex);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }).start();


    }

    private void runMessageReceiver() {
        new Thread(() -> {
            while (true) {
                for (Map.Entry<Socket, UserDataStream> socketDataInputStreamEntry : userSockets.entrySet()) {
                    try {
                        UserDataStream dataStream = socketDataInputStreamEntry.getValue();
                        ObjectInputStream data = dataStream.getDataInputStream();

                        Message message = (Message) data.readObject();

                        switch (message.getMessageType()) {
                            case MESSAGE:
                                for (Map.Entry<Socket, UserDataStream> userDataStreamEntry : userSockets.entrySet()) {
                                    if(userDataStreamEntry != socketDataInputStreamEntry){
                                        sendMessage(message, userDataStreamEntry.getValue());
                                    }
                                }
                                System.out.println(message.getUser().getClientLogin() + ": " + message.getText());
                                break;
                        }


                    } catch (IOException ex) {
                        System.out.println(ex);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }).start();


    }

    public void sendMessage(String text) {

        for (Map.Entry<Socket, UserDataStream> socketDataOutputStreamEntry : userSockets.entrySet()) {
            try {
                Message message = new Message(MessageType.MESSAGE, MessageSender.SERVER, text);
                ObjectOutputStream out = socketDataOutputStreamEntry.getValue().getDataOutputStream();
                out.writeObject(message);

            } catch (IOException ex) {
                System.out.println(ex);
            }

        }


    }
    public void sendMessage(Message message, UserDataStream userDataStream) {
            try {
                ObjectOutputStream out = userDataStream.getDataOutputStream();
                out.writeObject(message);
            } catch (IOException ex) {
                System.out.println(ex);
            }
    }
}