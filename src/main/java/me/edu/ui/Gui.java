package me.edu.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class Gui {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 500;

    // colors
    private static final Color LIGHT_GRAY = new Color(242, 242, 242);
    private static final Color GREEN = new Color(20, 202, 104);
    private static final Color RED = new Color(193, 60, 60);
    private static final Color WHITE = new Color(255, 255, 255);

    // fonts
    private static final Font SANS_18 = new Font(Font.SANS_SERIF, Font.PLAIN, 18);

    private Gui() {
    }

    private static JPanel createHeaderSection() {
        JPanel headerSection = new JPanel();

        // setting up the uri input
        JTextField inputUri = new JTextField();
        inputUri.setMaximumSize(new Dimension(600, 40));
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        inputUri.setBorder(padding);
        inputUri.setFont(SANS_18);

        // setting up the conenct button
        JButton connectButton = new JButton("conectar");
        connectButton.setBackground(GREEN);
        connectButton.setForeground(Color.white);
        connectButton.setMaximumSize(new Dimension(150, 40));
        connectButton.setFont(SANS_18);

        headerSection.add(inputUri);
        headerSection.add(connectButton);

        headerSection.setLayout(new BoxLayout(headerSection, BoxLayout.LINE_AXIS));

        return headerSection;
    }

    private static JPanel createDBsSection() {
        JPanel dbsSection = new JPanel();
        dbsSection.setOpaque(true);

        dbsSection.setLayout(new BorderLayout());

        JPanel container = new JPanel();

        dbsSection.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.weightx = 1.0;
        gbc.ipady = 40;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 1;

        for (int i = 0; i <= 10; i++) {
            gbc.gridy = i;
            dbsSection.add(createDbItem("Database " + i + 1), gbc);
        }

        return dbsSection;
    }

    private static JPanel createDbItem(String name) {
        JPanel panel = new JPanel();

        JLabel title = new JLabel(name);

        JButton removeButton = new JButton("remover");
        JButton connectButton = new JButton("Conectar");

        // panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setOpaque(true);
        panel.setBackground(WHITE);

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        panel.setBorder(padding);

        panel.add(title);
        panel.add(removeButton);
        panel.add(connectButton);

        return panel;
    }

    private static JPanel createQueryToolSection() {
        JPanel queryToolSection = new JPanel();
        queryToolSection.add(new JLabel("Query Tool Section"));
        queryToolSection.setOpaque(true);
        queryToolSection.setBackground(GREEN);

        return queryToolSection;
    }

    public static void init() {
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
        JPanel headerSection = createHeaderSection();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 70;
        mainPanel.add(headerSection, gbc);

        // creating and setting up databases section
        JPanel dbsSection = createDBsSection();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipady = 0;
        mainPanel.add(dbsSection, gbc);

        // creating and setting up query tool section
        JPanel queryToolSection = createQueryToolSection();
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(queryToolSection, gbc);

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