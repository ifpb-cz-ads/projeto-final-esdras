package me.edu.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.Border;

import me.edu.App;
import me.edu.ui.Gui;

public class DatabasesPanel extends JPanel {
    private List<String> databases;
    private CreateDBDialog createDBDialog = new CreateDBDialog(this);
    private ConfirmDeleteDialog confirmDeleteDialog = new ConfirmDeleteDialog(this);

    // constructor
    public DatabasesPanel() {

        // setting up panel
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(600, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

    }

    public void addDatabase(String databaseName) {
        List<String> newDatabases = new ArrayList<>();
        newDatabases.addAll(databases);
        newDatabases.add(databaseName);

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

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
    

        JLabel title = new JLabel(name);

        // panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        title.setOpaque(true);
        title.setBackground(Gui.WHITE);
        title.setMaximumSize(new Dimension(400, 40));
        title.setFont(Gui.SANS_18);

        JButton removeButton = Gui.createButton("remover", Gui.SANS_14_BOLD, Gui.RED, Gui.WHITE);
        removeButton.setMaximumSize(new Dimension(150, 40));

        // asking for confirmation
        removeButton.addActionListener(listener -> {
            confirmDeleteDialog.askConfirm(name);
        });

        // create connectio button
        JButton connectButton = Gui.createButton("conectar", Gui.SANS_14_BOLD, Gui.GREEN, Gui.WHITE);
        connectButton.setMaximumSize(new Dimension(150, 40));
        connectButton.addActionListener(listener -> {
            SwingWorker<Boolean, Void> swingWorker = new SwingWorker<Boolean, Void>() {
                @Override
                public Boolean doInBackground() {
                    App.connectToDatabase(name);
                    return true;
                }
            };

            swingWorker.execute();
        });

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        title.setBorder(padding);

        panel.add(title);
        panel.add(removeButton);
        panel.add(connectButton);

        return panel;
    }

    private void updateDatabasesListUI() {
        //updating height based on the number of databasese
        setPreferredSize(new Dimension(600, databases.size() * 150));

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
            add(createDbItem(database), gbc);
            gbc.gridy = ++i;
        }

        gbc.fill = GridBagConstraints.NONE;
        JButton createDbButton = Gui.createButton("criar db", Gui.SANS_18, Gui.DARK_BLUE, Gui.WHITE);
        createDbButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(createDbButton, gbc);

        createDbButton.addActionListener(listener -> {
            createDBDialog.setVisible(true);
        });

        revalidate();
    }
}
