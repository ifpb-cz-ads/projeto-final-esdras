package me.edu.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import me.edu.ui.Gui;

public class DatabasesPanel extends JPanel {
    private List<String> databases = Arrays.asList("Database", "Database", "Database");

    public DatabasesPanel() {
        setOpaque(true);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.weightx = 1.0;
        gbc.ipady = 40;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 1;

        JLabel sectionTitle = new JLabel("Databases");
        sectionTitle.setFont(Gui.SANS_24);
        add(sectionTitle, gbc);

        updateDatabasesListUI();
    }

    private JPanel createDbItem(String name) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        JLabel title = new JLabel(name);

        // panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        title.setOpaque(true);
        title.setBackground(Gui.WHITE);
        title.setMaximumSize(new Dimension(500, 40));
        title.setFont(Gui.SANS_18);

        JButton removeButton = Gui.createButton("remover", Gui.SANS_14_BOLD, Gui.RED, Gui.WHITE);
        removeButton.setMaximumSize(new Dimension(150, 40));

        JButton connectButton = Gui.createButton("conectar", Gui.SANS_14_BOLD, Gui.GREEN, Gui.WHITE);
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
            add(createDbItem(database + " " + i), gbc);
            gbc.gridy = ++i;
        }
    }
}
