package me.edu.components;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


import me.edu.controller.ClientController;
import me.edu.controller.DataController;
import me.edu.ui.Gui;

public class ConfirmDeleteDialog extends JDialog {
    private DatabasesPanel databasesPanel;
    private String dbName;

    public ConfirmDeleteDialog(DatabasesPanel databasesPanel) {
        super();

        this.databasesPanel = databasesPanel;

        // setting up dialog
        setLayout(new BorderLayout(8, 20));
        setSize(400, 200);
        setResizable(false);

        setTitle("Criar DB");

        JLabel titleLable = new JLabel("Tem certeza que deseja excluir o db?");

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
            List<String> newDbs = ClientController.deleteDatabase(dbName);
            DataController.updateDatabases(newDbs);
            databasesPanel.updateListUi();
            setVisible(false);
        });

        add(titleLable, BorderLayout.CENTER);
        add(confirmDeleteBtn, BorderLayout.SOUTH);
    }

    public void askConfirm(String databaseName) {
        dbName = databaseName;
        setVisible(true);
    }
}
