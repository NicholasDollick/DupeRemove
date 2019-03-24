import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Color;
import java.awt.Dimension;

public class ClientUI {
    JFileChooser fc = new JFileChooser();
    JTextPane pane = new JTextPane();
    private String dirToSearch = ""; // this needs to be fetced...or set?
    private String dirToMove = ""; // this needs to be fetched...or set?

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
        JButton runButton = new JButton("Run");
        JButton openButton = new JButton("Choose Dir");
        JLabel dirDisplay = new JLabel("File Thing Here");
        JButton saveToButton = new JButton("Move File To");
        JLabel dirToSaveDisplay = new JLabel("Moving File Here");
        JLabel boxDesc = new JLabel("Files Checked");
        pane.setEditable(false);

        openButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                fc.setCurrentDirectory(new File("."));
                fc.setDialogTitle("Folder Selection");
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (fc.showOpenDialog(openButton) == JFileChooser.APPROVE_OPTION) {
                    // nothing should really happen here
                }
                try {
                    String filePath = fc.getSelectedFile().getPath();
                    System.out.println(filePath);
                    dirDisplay.setText(filePath);
                    dirToSearch = filePath;
                } catch (Exception e) {
                    // add something here eventually
                }
            }
        });

        saveToButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                fc.setCurrentDirectory(new File("."));
                fc.setDialogTitle("Folder Selection");
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (fc.showOpenDialog(openButton) == JFileChooser.APPROVE_OPTION) {
                    // nothing should really happen here
                }
                try {
                    String filePath = fc.getSelectedFile().getPath();
                    System.out.println(filePath);
                    dirToSaveDisplay.setText(filePath);
                    dirToMove = filePath;
                } catch (Exception e) {
                    // add something here eventually
                }
            }
        });

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // this is what happens when the run button is clicked
                if (dirToMove.equals("") || dirToSearch.equals("")) {
                    // dont run if both paths arent set
                    System.out.println("Must Select Two Paths");
                } else {
                    // this should be logic execution
                    System.out.println("This would be a run");
                    addColoredText("text\n", Color.BLACK);
                    System.out.println(dirToMove);
                    System.out.println(dirToSearch);
                }
            }
        });

        // the functions used to add colored text
        addColoredText("Red Text\n", Color.RED);
        addColoredText("Blue Text\n", Color.BLACK);

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
        prePanel.add(saveToButton, preLeft);
        prePanel.add(dirToSaveDisplay, preRight);
        prePanel.add(boxDesc, preLeft);

        frame.add(prePanel, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane.setPreferredSize(new Dimension(650, 350));
        frame.getContentPane().add(pane, BorderLayout.CENTER);
        frame.getContentPane().add(runButton, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    public void addColoredText(String text, Color color) {
        StyledDocument doc = this.pane.getStyledDocument();

        Style style = this.pane.addStyle("Color Style", null);
        StyleConstants.setForeground(style, color);
        try {
            doc.insertString(doc.getLength(), text, style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}