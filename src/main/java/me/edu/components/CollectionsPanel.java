package me.edu.components;

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
import javax.swing.border.Border;

import me.edu.ui.Gui;

public class CollectionsPanel extends JPanel {
    List<String> collections;

    public JPanel createCollectionItem(String collectionName) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        JLabel titleLabel = new JLabel(collectionName);
        titleLabel.setOpaque(true);
        titleLabel.setMaximumSize(new Dimension(500, 40));
        titleLabel.setFont(Gui.SANS_18);
        titleLabel.setBackground(Gui.WHITE);

        JButton removeButton = Gui.createButton("remover", Gui.SANS_14_BOLD, Gui.RED, Gui.WHITE);
        removeButton.setMaximumSize(new Dimension(150, 40));

        JButton connectButton = Gui.createButton("conectar", Gui.SANS_14_BOLD, Gui.GREEN, Gui.WHITE);
        connectButton.setMaximumSize(new Dimension(150, 40));

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        titleLabel.setBorder(padding);

        panel.add(titleLabel);
        panel.add(removeButton);
        panel.add(connectButton);

        return panel;
    }

    public CollectionsPanel() {
        this.collections = new ArrayList<>();

        setOpaque(true);
        setLayout(new GridBagLayout());

        updateCollectionsUI();
    }

    public void updateCollectionsUI() {
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
        for (String collection : collections) {
            gbc.gridy = i++;
            add(createCollectionItem(collection), gbc);

        }

        revalidate();
    }

    public void updateCollections(List<String> collections) {
        this.collections = new ArrayList<>();
        this.collections.addAll(collections);

        updateCollectionsUI();
    }
}