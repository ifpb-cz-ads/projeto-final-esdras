package me.edu.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;

import me.edu.database.Connection;

public class Gui {
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 500;

    // threads
    private final Connection connection = new Connection(this);

    // colors
    private final Color LIGHT_GRAY = new Color(242, 242, 242);
    private final Color GREEN = new Color(20, 202, 104);
    private final Color RED = new Color(193, 60, 60);
    private final Color WHITE = new Color(255, 255, 255);
    private final Color BLUE = new Color(28, 161, 146);

    // fonts
    private final Font SANS_18 = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
    private final Font SANS_24 = new Font(Font.SANS_SERIF, Font.PLAIN, 24);
    private final Font SANS_14 = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    private final Font SANS_14_BOLD = new Font(Font.SANS_SERIF, Font.BOLD, 14);

    // panels
    JPanel databasesPanel = new JPanel();
    JPanel headerPanel = new JPanel();
    JPanel queryToolPanel = new JPanel();

    // data
    private List<String> databases = new ArrayList<>();

    public Gui() {
    }

    public void receiveData(List<String> data) {
        databases.addAll(data);
        updateDatabasesListUI();
    }

    private JButton createButton(String title, Font font) {
        JButton button = new JButton(title);
        button.setFont(font);
        button.setPreferredSize(new Dimension(150, 10));

        return button;
    }

    private JButton createButton(String title, Font font, Color background, Color foreground) {
        JButton button = createButton(title, font);
        button.setBackground(background);
        button.setForeground(foreground);

        return button;
    }

    private void configureHeaderPanel() {

        // setting up the uri input
        JTextField inputUri = new JTextField();
        inputUri.setMaximumSize(new Dimension(700, 40));
        inputUri.setText("http://localhost:27017/test");
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
        connectButton.addActionListener(connection);

        headerPanel.add(inputUri);
        headerPanel.add(connectButton);

        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.LINE_AXIS));

    }

    private JPanel createDbItem(String name) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        JLabel title = new JLabel(name);

        // panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        title.setOpaque(true);
        title.setBackground(WHITE);
        title.setMaximumSize(new Dimension(500, 40));
        title.setFont(SANS_18);

        JButton removeButton = createButton("remover", SANS_14_BOLD, RED, WHITE);
        removeButton.setMaximumSize(new Dimension(150, 40));

        JButton connectButton = createButton("conectar", SANS_14_BOLD, GREEN, WHITE);
        connectButton.setMaximumSize(new Dimension(150, 40));

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        title.setBorder(padding);

        panel.add(title);
        panel.add(removeButton);
        panel.add(connectButton);

        return panel;
    }

    private void updateDatabasesListUI() {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.weightx = 1.0;
        gbc.ipady = 40;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 1;

        int i = 1; // must start with 1
        for (String database : databases) {
            System.out.println(i);
            databasesPanel.add(createDbItem(database + " " + i), gbc);
            gbc.gridy = ++i;
        }
    }

    private void configureDatabasesPanel() {
        databasesPanel.setOpaque(true);
        databasesPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.weightx = 1.0;
        gbc.ipady = 40;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 1;

        JLabel sectionTitle = new JLabel("Databases");
        sectionTitle.setFont(SANS_24);
        databasesPanel.add(sectionTitle, gbc);

        updateDatabasesListUI();

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

        // creating and setting up databases section
        configureDatabasesPanel();
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