package me.kondi.sockets;


import me.kondi.sockets.User.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UserDataStream extends DataStream {

    public User user;
    public UserDataStream(ObjectInputStream dataInputStream, ObjectOutputStream dataOutputStream) {
        super(dataInputStream, dataOutputStream);
    }
    public UserDataStream(DataStream dataStream, User user) {
        super(dataStream.getDataInputStream(), dataStream.getDataOutputStream());
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
