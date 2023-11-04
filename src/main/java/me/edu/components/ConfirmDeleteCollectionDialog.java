package me.edu.components;

import me.edu.controller.ClientController;
import me.edu.ui.Gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ConfirmDeleteCollectionDialog extends JDialog {
    private String collectionName = null;
    public ConfirmDeleteCollectionDialog(){
        super();

        // setting up dialog
        setLayout(new BorderLayout(8, 20));
        setSize(400, 200);
        setResizable(false);

        setTitle("Criar DB");

        JLabel titleLable = new JLabel("Tem certeza que deseja a coleção " + collectionName + "?");

        titleLable.setFont(Gui.SANS_18);

        Border padding = BorderFactory.createEmptyBorder(8, 8, 8, 8);
        JPanel dialogPanel = (JPanel) getContentPane();
        dialogPanel.setBorder(padding);

        JButton confirmDeleteBtn = new JButton("remover");
        confirmDeleteBtn.setBackground(Gui.RED);
        confirmDeleteBtn.setFont(Gui.SANS_18);
        confirmDeleteBtn.setForeground(Gui.WHITE);

        // listener for create database
        confirmDeleteBtn.addActionListener(listener -> {
            ClientController.deleteCollection(collectionName);
            setVisible(false);
        });

        add(titleLable, BorderLayout.CENTER);
        add(confirmDeleteBtn, BorderLayout.SOUTH);
    }

    public void askConfirm(String collectionName){
        this.collectionName = collectionName;
        setVisible(true);
    }
}
