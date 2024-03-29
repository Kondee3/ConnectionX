package me.kondi.sockets;

import com.formdev.flatlaf.FlatLightLaf;
import me.kondi.sockets.Client.ConnectionXClient;
import me.kondi.sockets.Server.Server;

import javax.swing.*;
import java.awt.event.*;

public class ConnectionX extends JDialog {

    private JPanel contentPane;
    private JButton clientButton;
    private JTextField loginField;
    private JTextField passwordField;
    private JCheckBox rememberMeCheckBox;
    private JLabel connectionButton;


    public ConnectionX()   {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(clientButton);
        setTitle("ConnectionX");
        setResizable(false);

        clientButton.addActionListener(e -> {
            setVisible(false);
            onClient();

        });






        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });




    }

    private void onClient() {
        ConnectionXClient clientForm = new ConnectionXClient();
        clientForm.setupClientForm(loginField.getText(), passwordField.getText());

    }

    private static void onHost() {

        Server server = new Server();
        server.setupHost();


    }

    public void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        if (args.length == 0) {
            ConnectionX dialog = new ConnectionX();
            dialog.pack();
            dialog.setVisible(true);

        } else {
            if (args[0].equalsIgnoreCase("host")) {
                onHost();
            }
        }


    }

}

