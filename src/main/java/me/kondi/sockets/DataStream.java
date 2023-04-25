package me.kondi.sockets;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class DataStream {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;



    public HashMap<String, Object> userData = new HashMap<>();

    public DataStream(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    public DataStream(Socket s){
        try {
            this.dataOutputStream = new DataOutputStream(s.getOutputStream());
            this.dataInputStream = new DataInputStream(new BufferedInputStream(s.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public void setDataInputStream(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public Object getUserData(String tag) {
        return userData.get(tag);
    }

    public void setUserData(String tag, Object data) {
        userData.put(tag, data);
    }
}
