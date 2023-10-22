package me.edu.components;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import me.edu.ui.Gui;

public class DatabasesPanel extends JPanel {
    private List<String> databases;
    private CreateDBDialog createDBDialog = new CreateDBDialog(this);

    // constructor
    public DatabasesPanel() {

        // setting up panel
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
    }

    public void addDatabase(String databaseName) {
        List<String> newDatabases = new ArrayList<>();
        newDatabases.addAll(databases);
        newDatabases.add(databaseName);

        for (String db : newDatabases) {
            System.out.println(">>> " + db);
        }

        this.databases = new ArrayList<>(newDatabases);

        updateDatabasesListUI();
    }

    public void updateDatabases(List<String> databases) {
        this.databases = new ArrayList<>();
        this.databases.addAll(databases);

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

        // remove all databases before updating
        removeAll();

        // adding title
        gbc.gridy = 0;
        JLabel sectionTitle = new JLabel("Databases");
        sectionTitle.setFont(Gui.SANS_24);
        add(sectionTitle, gbc);

        int i = 1; // must start with 1
        gbc.gridy = i;
        for (String database : databases) {
            add(createDbItem(database + " " + i), gbc);
            gbc.gridy = ++i;
        }

        gbc.fill = GridBagConstraints.NONE;
        JButton createDbButton = Gui.createButton("criar db", Gui.SANS_18, Gui.DARK_BLUE, Gui.WHITE);
        add(createDbButton, gbc);

        createDbButton.addActionListener(listener -> {
            createDBDialog.setVisible(true);
        });

        revalidate();
    }
}
