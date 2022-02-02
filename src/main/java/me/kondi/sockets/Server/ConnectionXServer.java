package me.kondi.sockets.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConnectionXServer extends JDialog {

    private JPanel contentPanel;
    private JTextField messageField;
    private JTextArea chatField;
    private JButton sendButton;
    private Server server;

    public ConnectionXServer() {
        setContentPane(contentPanel);
        setModal(true);
        server = new Server(chatField, messageField);

        try {
            server.setupHost();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTitle("ConnectionX Host");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chatField.append("You: " + messageField.getText() + "\n");
                server.sendMessage();
                messageField.setText("");


            }
        });
        pack();
        setVisible(true);

    }



}
