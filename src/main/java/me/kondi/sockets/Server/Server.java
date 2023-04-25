package me.kondi.sockets.Server;

import me.kondi.sockets.DataStream;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    private ServerSocket ss;

    private HashMap<Socket, DataStream> sockets = new HashMap<>();


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

        while (true) {
            runConnectionManager();
            runMessageReceiver();
            runMessageSender();
        }


    }

    private void runMessageSender() {


        Runnable scan = () -> {
            while (true){
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
                    System.out.println("Client connected");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

        private void runMessageReceiver() {
        new Thread(() -> {
            while (true) {
                for (Map.Entry<Socket, DataStream> socketDataInputStreamEntry : sockets.entrySet()) {
                    try {
                        DataStream dataStream = socketDataInputStreamEntry.getValue();
                        DataInputStream data = dataStream.getDataInputStream();

                        JSONObject message = new JSONObject(data.readUTF());

                        switch (message.getString("type")){
                            case "login":
                                dataStream.setUserData("login", message.get("login"));
                                break;
                            case "message":
                                System.out.println(dataStream.getUserData("login") + ": " + message.get("text") + "\n");
                                break;
                        }


                    } catch (IOException ex) {
                        System.out.println(ex);
                    }

                }
            }
        }).start();


    }

    public void sendMessage(String text) {

        for (Map.Entry<Socket, DataStream> socketDataOutputStreamEntry : sockets.entrySet()) {
            try {
                socketDataOutputStreamEntry.getValue().getDataOutputStream().writeUTF(text);
                System.out.println("Host:" + text);
            } catch (IOException ex) {
                System.out.println(ex);
            }

        }


    }
}