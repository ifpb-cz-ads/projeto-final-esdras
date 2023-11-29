package me.edu.view;

import me.edu.controller.ClientController;
import me.edu.controller.DataController;
import me.edu.ui.Gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CreateCollectionDialog extends JDialog {

    public CreateCollectionDialog(){
        super();

        // setting up dialog
        setLayout(new BorderLayout(8, 20));
        setSize(400, 200);
        setResizable(false);

        setTitle("Criar DB");

        JLabel titleLable = new JLabel("Nome");

        JTextField titleField = new JTextField();

        titleLable.setFont(Gui.SANS_18);
        titleField.setFont(Gui.SANS_18);

        Border padding = BorderFactory.createEmptyBorder(8, 8, 8, 8);
        titleField.setBorder(padding);
        JPanel dialogPanel = (JPanel) getContentPane();
        dialogPanel.setBorder(padding);

        JButton confirmCreateBtn = Gui.createButton("criar", Gui.SANS_18, Gui.DARK_BLUE, Gui.WHITE, 200, 30);

        // listener for create database
        confirmCreateBtn.addActionListener(listener -> {
            new SwingWorker<Boolean, Void>(){
                @Override
                public Boolean doInBackground(){
                    ClientController.createCollection(titleField.getText());
                    DataController.addCollection(titleField.getText());
                    setVisible(false);
                    return true;
                }
            }.execute();
        });

        add(titleLable, BorderLayout.NORTH);
        add(titleField, BorderLayout.CENTER);
        add(confirmCreateBtn, BorderLayout.SOUTH);
    }
}
