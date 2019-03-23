import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Color;
import java.awt.Dimension;

public class ClientUI {
    String name = "DupeRemove";
    JFrame chatFrame = new JFrame(name);
    JFrame preFrame;
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
        JFrame frame = new JFrame("DupeRemove");
        JTextPane pane = new JTextPane();
        JButton button = new JButton("Run");
        JButton openButton = new JButton("Choose Dir");
        JLabel dirDisplay = new JLabel("File Thing Here");
        JLabel boxDesc = new JLabel("Files Checked");
        pane.setEditable(false);

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

        // the functions used to add colored text
        addColoredText(pane, "Red Text\n", Color.RED);
        addColoredText(pane, "Blue Text\n", Color.BLUE);

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
        prePanel.add(boxDesc, preLeft);

        frame.add(prePanel, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane.setPreferredSize(new Dimension(650, 350));
        frame.getContentPane().add(pane, BorderLayout.CENTER);
        frame.getContentPane().add(button, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

    }

    public void addColoredText(JTextPane pane, String text, Color color) {
        StyledDocument doc = pane.getStyledDocument();

        Style style = pane.addStyle("Color Style", null);
        StyleConstants.setForeground(style, color);
        try {
            doc.insertString(doc.getLength(), text, style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}