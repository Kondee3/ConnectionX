package me.kondi.sockets;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import me.kondi.sockets.Client.Client;
import me.kondi.sockets.Client.ConnectionXClient;
import me.kondi.sockets.Server.ConnectionXServer;
import me.kondi.sockets.Server.Server;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class ConnectionX extends JDialog {

    private JPanel contentPane;
    private JButton clientButton;
    private JButton hostButton;


    public ConnectionX() {


        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(clientButton);
        setTitle("ConnectionX");
        clientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClient();
                dispose();
            }
        });

        hostButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onHost();
                dispose();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onClient() {
       ConnectionXClient clientForm = new ConnectionXClient();


        // add your code here



    }

    private void onHost() {
        ConnectionXServer serverForm = new ConnectionXServer();






    }

    public void onCancel(){
        dispose();
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        ConnectionX dialog = new ConnectionX();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
