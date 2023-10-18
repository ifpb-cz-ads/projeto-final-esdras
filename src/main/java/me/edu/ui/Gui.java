package me.edu.ui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui {
    private Gui() {
    }

    private static JPanel createUriPanel() {
        // creating the uri input panel
        JPanel uriPanel = new JPanel();
        // uri input
        JTextField uriInput = new JTextField(50);
        uriPanel.setLayout(new BoxLayout(uriPanel, BoxLayout.LINE_AXIS));

        // connect button
        JButton button = new JButton("conectar");
        button.setBackground(new Color(28, 61, 146));
        button.setForeground(Color.white);

        uriPanel.add(uriInput);
        uriPanel.add(button);

        return uriPanel;
    }

    public static void init() {
        JFrame windowFrame = new JFrame();

        // getting all panels
        JPanel uriPanel = createUriPanel();

        windowFrame.setSize(800, 600);
        windowFrame.setResizable(false);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // adding all the panels
        windowFrame.add(uriPanel);

        windowFrame.setVisible(true);
    }
}
