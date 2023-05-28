package me.kondi.sockets.User;

import java.io.Serializable;

public class User implements Serializable {
    private String clientId;
    private String clientLogin;
    private String password;

    public User(String clientLogin, String password) {
        this.clientLogin = clientLogin;
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientLogin() {
        return clientLogin;
    }

    public void setClientLogin(String clientLogin) {
        this.clientLogin = clientLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
