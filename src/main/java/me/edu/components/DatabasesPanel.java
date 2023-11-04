package me.edu.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import me.edu.controller.ClientController;
import me.edu.controller.DataController;
import me.edu.ui.Gui;

/**
 * Handle the databases list(UI) and control updating(UI)*/
public class DatabasesPanel extends JPanel {
    private CreateDBDialog createDBDialog = new CreateDBDialog(this);
    private ConfirmDeleteDialog confirmDeleteDialog = new ConfirmDeleteDialog(this);

    // constructor
    public DatabasesPanel() {
        // setting up panel
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(600, 0));
    }

    private JPanel createDbItem(String name) {

        SwingWorker<Boolean, Void> removeWorker = new SwingWorker<Boolean,Void>() {
          @Override
          public Boolean doInBackground(){
              confirmDeleteDialog.askConfirm(name);
              return true;
          }
        };

        SwingWorker<Boolean, Void> connectWorker = new SwingWorker<Boolean, Void>() {
          @Override
          public Boolean doInBackground() {
            ClientController.connectToDatabase(name);
            return true;
          }
        };

        JPanel panel = Gui.createListItem(name, connectWorker, removeWorker);

        return panel;
    }

    public void updateListUi() {
        //updating height based on the number of databasese
        setPreferredSize(new Dimension(600, DataController.getDatabases().size() * 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.weightx = 1.0;
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
        for (String database : DataController.getDatabases()) {
            add(createDbItem(database), gbc);
            gbc.gridy = ++i;
        }

        gbc.fill = GridBagConstraints.NONE;
        JButton createDbButton = Gui.createButton("criar db", Gui.SANS_18, Gui.DARK_BLUE, Gui.WHITE, 200, 40);
        add(createDbButton, gbc);

        createDbButton.addActionListener(listener -> {
            createDBDialog.setVisible(true);
        });

        revalidate();
    }
}
