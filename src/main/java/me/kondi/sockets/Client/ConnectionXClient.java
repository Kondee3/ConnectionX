package me.kondi.sockets.Client;

import javax.swing.*;

public class ConnectionXClient extends JDialog {

    private JPanel contentPanel;
    private JTextArea chatField;
    private JTextField messageField;
    private JButton sendButton;
    private Client client;

    public void setupClientForm(String login) {

        setContentPane(contentPanel);
        setModal(true);
        setTitle("ConnectionX Client");

        contentPanel.getRootPane().setDefaultButton(sendButton);

        pack();
        client = new Client(chatField, messageField, login);



        sendButton.addActionListener(e -> {
            chatField.append("You: " + messageField.getText() + "\n");
            client.sendMessage();
            messageField.setText("");
        });

        setVisible(true);
    }


}
