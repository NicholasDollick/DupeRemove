import java.io.*;
import java.awt.*;
import java.net.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.awt.event.*;
import javax.net.ssl.SSLSocket;
import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.*;

public class ClientUI {
    String name = "DupeRemove";
    JFrame chatFrame = new JFrame(name);
    JTextArea chatBox;
    JButton sendMessage;
    JFrame preFrame;
    JTextField usernameBox;
    JPasswordField passwordBox;
    JTextField serverIPInput;
    JTextField portNum;
    JFileChooser fc = new JFileChooser();
    JButton openButton;
    JTextArea textArea = new JTextArea();

    public static void main(String[] args) {
        new ClientUI();
    }

    // constructor
    public ClientUI() {
        // match UI of user desktop environment
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatFrame.setVisible(false);
        preFrame = new JFrame(name);
        JButton enterServer = new JButton("Enter Chat Server");
        openButton = new JButton("Choose A Directory");
        JLabel dirDisplay = new JLabel("File Thing Here");

        openButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                fc.setCurrentDirectory(new File("."));
                fc.setDialogTitle("Hello World");
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (fc.showOpenDialog(openButton) == JFileChooser.APPROVE_OPTION) {
                    // nothing should really happen here
                }
                try {
                    String file = fc.getSelectedFile().getPath();
                    System.out.println(file);
                    dirDisplay.setText(file);
                } catch (Exception e) {
                    // add something here eventually
                }
            }
        });

        JButton createAccount = new JButton("Create Account");
        JPanel prePanel = new JPanel(new GridBagLayout());

        GridBagConstraints preRight = new GridBagConstraints();
        preRight.insets = new Insets(0, 0, 10, 10);
        preRight.anchor = GridBagConstraints.EAST;
        GridBagConstraints preLeft = new GridBagConstraints();
        GridBagConstraints preBottom = new GridBagConstraints();
        preLeft.anchor = GridBagConstraints.WEST;
        preLeft.insets = new Insets(0, 10, 10, 10);
        preRight.fill = GridBagConstraints.HORIZONTAL;
        preRight.gridwidth = GridBagConstraints.REMAINDER;

        preBottom.insets = new Insets(10, 10, 10, 10);
        preBottom.anchor = GridBagConstraints.SOUTH;

        prePanel.add(openButton, preLeft);
        prePanel.add(dirDisplay, preRight);
        prePanel.add(textArea, preBottom);

        preFrame.add(prePanel, BorderLayout.CENTER);
        preFrame.add(createAccount, BorderLayout.SOUTH);
        preFrame.add(enterServer, BorderLayout.SOUTH);
        preFrame.setSize(650, 350);
        preFrame.setVisible(true);

    }
}