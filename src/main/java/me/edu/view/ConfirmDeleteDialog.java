package me.edu.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;


import me.edu.controller.ClientController;
import me.edu.ui.Gui;

public class ConfirmDeleteDialog extends JDialog {
    private String dbName = null;
    JLabel titleLable = new JLabel();

    public ConfirmDeleteDialog(DatabasesPanel databasesPanel) {
        super();

        // setting up dialog
        setLayout(new BorderLayout(8, 20));
        setSize(400, 200);
        setBounds(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 200,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 100,
                400,
                200
        );
        setResizable(false);

        setTitle("Remover DB");


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
            new SwingWorker<Boolean, Void>(){
                @Override
                public Boolean doInBackground(){
                    ClientController.deleteDatabase(dbName);
                    return  true;
                }
            }.execute();

            setVisible(false);
        });

        add(titleLable, BorderLayout.CENTER);
        add(confirmDeleteBtn, BorderLayout.SOUTH);
    }

    public void askConfirm(String databaseName) {
        titleLable.setText("Quer mesmo excluir: " + databaseName + "?");
        setVisible(true);
    }
}
