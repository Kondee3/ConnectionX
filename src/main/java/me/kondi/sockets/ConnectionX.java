package me.kondi.sockets;

import com.formdev.flatlaf.FlatLightLaf;
import me.kondi.sockets.Client.ConnectionXClient;
import me.kondi.sockets.Server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionX extends JDialog {

    private JPanel contentPane;
    private JButton clientButton;
    private JTextField loginField;
    private JTextField passwordField;
    private JCheckBox rememberMeCheckBox;





    public ConnectionX()   {



        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(clientButton);
        setTitle("ConnectionX");
        setSize(280, 360);
        clientButton.addActionListener(e -> {
            setVisible(false);
            onClient();

        });
        clientButton.setBackground(new Color(64, 61, 57));






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

