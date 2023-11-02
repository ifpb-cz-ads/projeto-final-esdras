package me.edu.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import me.edu.controller.DataController;
import me.edu.ui.Gui;

/**
 * Hanldes the collecitons to display it on the ui*/
public class CollectionsPanel extends JPanel {

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
            System.out.println("Connect to collection");
            return true;
          }
        };

        SwingWorker<Boolean, Void> removeWorker = new SwingWorker<Boolean,Void>() {
          @Override
          public Boolean doInBackground(){
              System.out.println("Remove collection");
              return true;
          }
        };
    
        JPanel panel = Gui.createListItem(collectionName, connectWorker, removeWorker);
        return panel;
    }

    /**
     * Updates the ui with the collections set*/
    public void updateListUi() {
        //updating height based on the number of databasese
        setPreferredSize(new Dimension(600, DataController.getCollections().size() * 150));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
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

        revalidate();
    }

}
