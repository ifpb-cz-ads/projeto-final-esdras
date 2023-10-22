package me.edu.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.mongodb.MongoClient;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoIterable;

import me.edu.components.DatabasesPanel;
import me.edu.database.MClient;

public class Gui {
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 500;

    // threads

    // colors
    public static final Color LIGHT_GRAY = new Color(242, 242, 242);
    public static final Color GREEN = new Color(20, 202, 104);
    public static final Color RED = new Color(193, 60, 60);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color BLUE = new Color(28, 161, 146);
    public static final Color DARK_BLUE = new Color(3, 25, 82);

    // fonts
    public static final Font SANS_18 = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
    public static final Font SANS_24 = new Font(Font.SANS_SERIF, Font.PLAIN, 24);
    public static final Font SANS_14 = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    public static final Font SANS_14_BOLD = new Font(Font.SANS_SERIF, Font.BOLD, 14);

    // panels
    DatabasesPanel databasesPanel = new DatabasesPanel();
    JPanel headerPanel = new JPanel();
    JPanel queryToolPanel = new JPanel();

    // elements
    private MongoClient mongoClient;
    private JTextField inputUri;

    public Gui() {
    }

    public void receiveData(List<String> data) {
        databasesPanel.updateDatabases(data);
    }

    public static JButton createButton(String title, Font font) {
        JButton button = new JButton(title);
        button.setFont(font);
        button.setPreferredSize(new Dimension(150, 10));

        return button;
    }

    public static JButton createButton(String title, Font font, Color background, Color foreground) {
        JButton button = createButton(title, font);
        button.setBackground(background);
        button.setForeground(foreground);

        return button;
    }

    private void configureHeaderPanel() {

        // setting up the uri input
        inputUri = new JTextField();
        inputUri.setMaximumSize(new Dimension(700, 40));
        inputUri.setText("localhost:27017");
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        inputUri.setBorder(padding);
        inputUri.setFont(SANS_18);

        // setting up the conenct button
        JButton connectButton = new JButton("conectar");
        connectButton.setBackground(BLUE);
        connectButton.setForeground(Color.white);
        connectButton.setMaximumSize(new Dimension(150, 40));
        connectButton.setFont(SANS_18);

        // adding listener
        connectButton.addActionListener(listener -> {
            SwingWorker swingWorker = new SwingWorker<Boolean, Void>() {
                @Override
                public Boolean doInBackground() {
                    mongoClient = MClient.get(inputUri.getText());
                    MongoIterable<String> dbList = mongoClient.listDatabaseNames();

                    List<String> allDatabases = new ArrayList<>();
                    for (String db : dbList)
                        allDatabases.add(db);

                    databasesPanel.updateDatabases(allDatabases);
                    return true;
                }

            };

            swingWorker.execute();
        });

        headerPanel.add(inputUri);
        headerPanel.add(connectButton);

        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.LINE_AXIS));

    }

    private void configureQueryToolPanel() {
        queryToolPanel.setOpaque(true);

        queryToolPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.weightx = 1.0;
        gbc.ipady = 40;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 1;

        gbc.gridy = 0;
        JLabel sectionTitle = new JLabel("Query Tool");
        sectionTitle.setFont(SANS_24);
        queryToolPanel.add(sectionTitle, gbc);

        Border padding = BorderFactory.createEmptyBorder(10, 10, 0, 10);

        gbc.gridy = 1;
        JTextArea queryArea = new JTextArea();
        queryArea.setBorder(padding);
        queryArea.setFont(SANS_18);
        queryArea.setWrapStyleWord(true);
        queryArea.setLineWrap(true);
        queryToolPanel.add(queryArea, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        JButton queryButton = createButton("Requisitar", SANS_18, BLUE, WHITE);
        queryToolPanel.add(queryButton, gbc);

        gbc.gridy = 3;
        JLabel sectionTitle2 = new JLabel("Response");
        sectionTitle.setFont(SANS_18);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        queryToolPanel.add(sectionTitle2, gbc);

        gbc.gridy = 4;
        JTextPane queryResponsePane = new JTextPane();
        queryResponsePane.setText("Faça uma requsição para obter respostas");
        queryResponsePane.setBorder(padding);
        queryResponsePane.setEditable(false);
        queryToolPanel.add(queryResponsePane, gbc);

    }

    public void init() {
        JFrame windowFrame = new JFrame("MJ");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.fill = GridBagConstraints.BOTH;

        // creating and setting up header section
        configureHeaderPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 70;
        mainPanel.add(headerPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipady = 0;
        mainPanel.add(databasesPanel, gbc);

        // creating and setting up query tool section
        configureQueryToolPanel();
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(queryToolPanel, gbc);

        // Create a JScrollPane to make the window scrollable
        JScrollPane scrollPane = new JScrollPane(mainPanel);

        windowFrame.add(scrollPane);

        // customizing scrollbar
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = GREEN;
                this.trackColor = LIGHT_GRAY;

            }

            // removing the scrollbar buttons
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton increaseScrollbarButton = new JButton();
                increaseScrollbarButton.setPreferredSize(new Dimension(0, 0));
                increaseScrollbarButton.setMinimumSize(new Dimension(0, 0));
                increaseScrollbarButton.setMaximumSize(new Dimension(0, 0));
                return increaseScrollbarButton;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton decreaseScrollbarButton = new JButton();
                decreaseScrollbarButton.setPreferredSize(new Dimension(0, 0));
                decreaseScrollbarButton.setMinimumSize(new Dimension(0, 0));
                decreaseScrollbarButton.setMaximumSize(new Dimension(0, 0));
                return decreaseScrollbarButton; // Create a dummy button for the bottom arrow
            }

        });

        windowFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setResizable(false);
        windowFrame.setVisible(true);
    }

};