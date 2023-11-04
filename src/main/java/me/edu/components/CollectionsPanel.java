package me.edu.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

import me.edu.controller.ClientController;
import me.edu.controller.DataController;
import me.edu.ui.Gui;

/**
 * Hanldes the collecitons to display it on the ui*/
public class CollectionsPanel extends JPanel {
    CreateCollectionDialog createCollectionDialog = new CreateCollectionDialog();
    ConfirmDeleteCollectionDialog confirmDeleteCollectionDialog = new ConfirmDeleteCollectionDialog();

    /**
     * Constructor*/
    public CollectionsPanel() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(600, 40));

    }

    /**
     * Creates a UI item to add to a list container*/
    public JPanel createCollectionItem(String collectionName) {
        SwingWorker<Boolean, Void> connectWorker = new SwingWorker<Boolean,Void>() {
          @Override
          protected Boolean doInBackground(){
            ClientController.setTargetCollection(collectionName);
            return true;
          }
        };

        SwingWorker<Boolean, Void> removeWorker = new SwingWorker<Boolean,Void>() {
          @Override
          public Boolean doInBackground(){
              confirmDeleteCollectionDialog.askConfirm(collectionName);
              return true;
          }
        };
    
        JPanel panel = Gui.createListItem(collectionName, connectWorker, removeWorker);
        return panel;
    }

    /**
     * Updates the ui with the collections set*/
    public void updateListUi() {
        removeAll();
        //updating height based on the number of databasese
        setPreferredSize(new Dimension(600, DataController.getCollections().size() * 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        gbc.gridy = 0;
        JLabel titleLabel = new JLabel("Coleções");
        titleLabel.setFont(Gui.SANS_24);
        add(titleLabel, gbc);

        int i = 1;
        for (String collection : DataController.getCollections()) {
            gbc.gridy = i++;
            add(createCollectionItem(collection), gbc);

        }

        gbc.gridy = ++i;
        gbc.fill = GridBagConstraints.NONE;
        JButton createCollectionButton = Gui.createButton("criar coleção", Gui.SANS_18, Gui.DARK_BLUE, Gui.WHITE, 200, 40);
        add(createCollectionButton, gbc);


        createCollectionButton.addActionListener(l -> {
            createCollectionDialog.setVisible(true);
        });

        revalidate();
    }

}
