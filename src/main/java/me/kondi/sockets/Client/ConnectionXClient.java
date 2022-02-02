package me.kondi.sockets.Client;

import me.kondi.sockets.Server.ConnectionXServer;
import me.kondi.sockets.Server.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConnectionXClient extends JDialog {

    private JPanel contentPanel;
    private JTextArea chatField;
    private JTextField messageField;
    private JButton sendButton;
    private Client client;

    public ConnectionXClient() {
        setContentPane(contentPanel);
        setModal(true);
        client = new Client(chatField, messageField);

        try {
            client.setupClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTitle("ConnectionX Client");


        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chatField.append("You: " + messageField.getText() + "\n");
                client.sendMessage();
                messageField.setText("");


            }
        });
        pack();
        setVisible(true);



    }



}
