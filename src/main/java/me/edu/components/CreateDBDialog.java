package me.edu.components;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import me.edu.ui.Gui;

public class CreateDBDialog extends JDialog {

    public CreateDBDialog() {
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

        JButton confirmCreateBtn = new JButton("criar");
        confirmCreateBtn.setBackground(Gui.DARK_BLUE);
        confirmCreateBtn.setFont(Gui.SANS_18);
        confirmCreateBtn.setForeground(Gui.WHITE);

        add(titleLable, BorderLayout.NORTH);
        add(titleField, BorderLayout.CENTER);
        add(confirmCreateBtn, BorderLayout.SOUTH);
    }

}
