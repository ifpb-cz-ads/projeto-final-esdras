package me.edu.components;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

import me.edu.controller.ClientController;
import me.edu.ui.Gui;

public class DocumentsPanel extends JPanel {
  public DocumentsPanel(){
        // setting up panel
      setLayout(new GridBagLayout());
      setPreferredSize(new Dimension(600, 0));
  }

  private JPanel createFilterPanel(){
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout());

    JTextField keyInput = new JTextField("chave");
    JTextField valueInput = new JTextField("value");

    keyInput.setFont(Gui.SANS_14);
    valueInput.setFont(Gui.SANS_14);

    Border padding = BorderFactory.createEmptyBorder(4, 4, 4, 4);

    keyInput.setBorder(padding);
    valueInput.setBorder(padding);

    keyInput.setPreferredSize(new Dimension(100, 30));
    valueInput.setPreferredSize(new Dimension(100, 30));

    JButton filterButton = new JButton("filtrar");
    filterButton.setFont(Gui.SANS_14);
    filterButton.setForeground(Gui.WHITE);
    filterButton.setBackground(Gui.DARK_BLUE);
    filterButton.setPreferredSize(new Dimension(100, 30));

    filterButton.addActionListener(l -> {
      ClientController.filterKey = keyInput.getText().isBlank() || keyInput.getText().isEmpty() ? null : keyInput.getText();
      ClientController.filterValue = valueInput.getText().isBlank() || valueInput.getText().isEmpty() ? null : valueInput.getText();
      updateDocumentsUi();
    });

    panel.add(keyInput);
    panel.add(valueInput);
    panel.add(filterButton);

    return panel;
  }

  public void updateDocumentsUi(){
    removeAll();
    setPreferredSize(new Dimension(600, 600));
    
    JLabel label = new JLabel("Doc Tool");
    label.setFont(Gui.SANS_18);

    JLabel filteredLabel = new JLabel(
            ClientController.filterKey != null && ClientController.filterValue != null ?
                    "Filtrado" :
                    "Geral"
    );
    filteredLabel.setFont(Gui.SANS_18);
    filteredLabel.setForeground(ClientController.filterKey != null && ClientController.filterValue != null ?
            Gui.BLUE :
            Gui.DARK_BLUE);

    JTextArea textArea = new JTextArea();
    textArea.setText(ClientController.getJsonDocuments());
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);

    textArea.setFont(Gui.SANS_14);

    Border padding = BorderFactory.createEmptyBorder(4, 4, 4, 4);
    textArea.setBorder(padding);

    GridBagConstraints gbc = new GridBagConstraints();

    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.anchor = GridBagConstraints.LINE_START;
    gbc.gridwidth = 1;

    gbc.gridy = 0;
    add(label, gbc);

    gbc.gridy = 1;
    add(filteredLabel, gbc);

    gbc.gridy = 2;
    add(createFilterPanel(), gbc);

    JScrollPane scrollArea = new JScrollPane(textArea);
    scrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollArea.setPreferredSize(new Dimension(600, 300));

    gbc.gridy = 3;
    add(scrollArea, gbc);
    
    JButton saveButton = Gui.createButton("Salvar", Gui.SANS_14, Gui.DARK_BLUE, Gui.WHITE, 100, 30);
    saveButton.setPreferredSize(new Dimension(100, 40));

    //worker to update collection
    SwingWorker<Boolean, Void> swingWorker = new SwingWorker<Boolean, Void>() {
      @Override
      protected Boolean doInBackground() throws Exception {
        ClientController.updateServerCollection(textArea.getText());
        return true;
      }
    };

    saveButton.addActionListener(l -> {
        swingWorker.execute();
    });
    
    gbc.gridy = 4;
    add(saveButton, gbc);

    revalidate();
  }
}
