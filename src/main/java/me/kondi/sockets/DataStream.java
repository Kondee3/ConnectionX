package me.kondi.sockets;

import me.kondi.sockets.User.User;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class DataStream{
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;


    public DataStream(ObjectInputStream dataInputStream, ObjectOutputStream dataOutputStream) {
        this.inputStream = dataInputStream;
        this.outputStream = dataOutputStream;
    }

    public DataStream(Socket s){
        try {
            this.outputStream = new ObjectOutputStream(s.getOutputStream());
            this.inputStream = new ObjectInputStream(s.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ObjectInputStream getDataInputStream() {
        return inputStream;
    }

    public void setDataInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ObjectOutputStream getDataOutputStream() {
        return outputStream;
    }

    public void setDataOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }


}
