package me.kondi.sockets.Message;

import me.kondi.sockets.User.User;

import java.io.Serializable;

public class Message implements Serializable {
    private MessageType messageType;

    private MessageSender messageSender;
    private String text;

    private User user;

    public Message(MessageType messageType, MessageSender messageSender, User user) {
        this.messageType = messageType;
        this.messageSender = messageSender;
        this.user = user;

    }

    public Message(MessageType messageType, MessageSender messageSender) {
        this.messageType = messageType;
        this.messageSender = messageSender;

    }

    public Message(MessageType messageType, MessageSender messageSender, User user, String text) {
        this.messageType = messageType;
        this.messageSender = messageSender;
        this.user = user;
        this.text = text;
    }

    public Message(MessageType messageType, MessageSender messageSender,String text) {
        this.messageType = messageType;
        this.messageSender = messageSender;
        this.user = user;
        this.text = text;
    }


    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
