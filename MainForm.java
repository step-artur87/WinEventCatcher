import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Art
 * Date: 27.10.14
 * Time: 7:25
 * To change this template use File | Settings | File Templates.
 */

public class MainForm implements ActionListener, KeyListener {
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton button10;
    private JButton button11;
    private JButton button12;
    private JButton button13;
    private JButton button14;
    private JButton button15;
    private JButton button16;
    private JButton button17;
    private JButton button18;
    private JButton button19;
    private JButton button20;
    private JButton button21;
    private JPanel rootPanel;
    private JPanel buttonPanel;
    private JPanel textPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JTextField textField13;
    private JTextField textField14;
    private JTextField textField15;
    private JTextField textField16;
    private JTextField textField17;
    private JTextField textField18;
    private JTextField textField19;
    private JTextField textField20;
    private JTextField textField21;
    CardLayout cardLayout;

    private JButton[] buttons = new JButton[21];
    private JTextField[] textFields = new JTextField[21];
    private Properties tagsProp = new Properties();
    File eventsFile = new File("Events.txt");
    File tagsFile = new File("Tags.txt");
    private boolean stateButton = true;

    public MainForm() {
        buttons[0] = button1;
        buttons[1] = button2;
        buttons[2] = button3;
        buttons[3] = button4;
        buttons[4] = button5;
        buttons[5] = button6;
        buttons[6] = button7;
        buttons[7] = button8;
        buttons[8] = button9;
        buttons[9] = button10;
        buttons[10] = button11;
        buttons[11] = button12;
        buttons[12] = button13;
        buttons[13] = button14;
        buttons[14] = button15;
        buttons[15] = button16;
        buttons[16] = button17;
        buttons[17] = button18;
        buttons[18] = button19;
        buttons[19] = button20;
        buttons[20] = button21;

        textFields[0] = textField1;
        textFields[1] = textField2;
        textFields[2] = textField3;
        textFields[3] = textField4;
        textFields[4] = textField5;
        textFields[5] = textField6;
        textFields[6] = textField7;
        textFields[7] = textField8;
        textFields[8] = textField9;
        textFields[9] = textField10;
        textFields[10] = textField11;
        textFields[11] = textField12;
        textFields[12] = textField13;
        textFields[13] = textField14;
        textFields[14] = textField15;
        textFields[15] = textField16;
        textFields[16] = textField17;
        textFields[17] = textField18;
        textFields[18] = textField19;
        textFields[19] = textField20;
        textFields[20] = textField21;

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(this);
            buttons[i].addKeyListener(this);
            textFields[i].addKeyListener(this);
        }

        this.loadTags();
        cardLayout = (CardLayout) rootPanel.getLayout();
    }

    public static void main(String[] args) {
        System.setErr(System.out);

        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().rootPanel);
        frame.setDefaultCloseOperation(
                WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void loadTags() {
        FileInputStream fileInputStream = null;

        try {
            tagsFile.createNewFile();
            fileInputStream = new FileInputStream(tagsFile);
            tagsProp.load(fileInputStream);

            tagsProp.keySet().forEach(
                    (key) -> {
                        int i = Integer.parseInt((String) key);
                        if ((i >= 0) && (i < buttons.length)) {
                            buttons[i].setText(tagsProp.getProperty(
                                    (String) key));
                            textFields[i].setText(tagsProp.getProperty(
                                    (String) key));
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTags() {
        FileOutputStream fileOutputStream = null;

        try {
            tagsFile.createNewFile();
            fileOutputStream = new FileOutputStream(tagsFile);
            tagsProp.clear();

            for (int i = 0; i < buttons.length; i++) {
                if (!textFields[i].getText().equals("")) {
                    tagsProp.setProperty(String.valueOf(i), textFields[i].getText());
                }
            }

            tagsProp.store(fileOutputStream, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void append(String s) {
        FileOutputStream fileOutputStream = null;

        try {
            eventsFile.createNewFile();
            fileOutputStream = new FileOutputStream(eventsFile, true);
            fileOutputStream.write((int) '\r');
            fileOutputStream.write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("")
                || ((e.getModifiers() & InputEvent.CTRL_MASK) > 0)) {
            cardLayout.last(rootPanel);
            stateButton = false;

            for (int i = 0; i < buttons.length; i++) {
                if (e.getSource().equals(buttons[i])) {
                    textFields[i].requestFocus();
                }
            }
        } else {
            Calendar calendar = Calendar.getInstance();
            Formatter formatter = new Formatter();
            formatter.format("%td.%tm.%ty %tR:%tS",
                    calendar,
                    calendar,
                    calendar,
                    calendar,
                    calendar);
            String now = formatter.toString();

            this.append(e.getActionCommand() + " " + now);

            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                if (stateButton) {
                    System.exit(0);
                } else {
                    cardLayout.first(rootPanel);
                    loadTags();
                    stateButton = true;
                    button1.requestFocus();
                }
                break;
            case KeyEvent.VK_ENTER:
                if (!stateButton) {
                    saveTags();
                    cardLayout.first(rootPanel);
                    loadTags();
                    stateButton = true;
                    button1.requestFocus();
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
