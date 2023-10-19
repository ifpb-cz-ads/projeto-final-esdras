package me.edu.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gui {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 500;

    private static final Color LIGHT_GRAY = new Color(242, 242, 242);
    private static final Color GREEN = new Color(20, 202, 104);
    private static final Color RED = new Color(193, 60, 60);

    private Gui() {
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

        JPanel headerSection = new JPanel();
        headerSection.add(new JLabel("Header Section"));
        headerSection.setOpaque(true);
        headerSection.setBackground(GREEN);
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(headerSection, gbc);

        JPanel dbsSection = new JPanel();
        dbsSection.add(new JLabel("DBs Section"));
        dbsSection.setOpaque(true);
        dbsSection.setBackground(GREEN);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(dbsSection, gbc);

        JPanel queryToolSection = new JPanel();
        queryToolSection.add(new JLabel("Query Tool Section"));
        queryToolSection.setOpaque(true);
        queryToolSection.setBackground(GREEN);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(queryToolSection, gbc);

        windowFrame.add(mainPanel);

        windowFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setVisible(true);
    }
};