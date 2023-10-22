package me.edu.database;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.edu.ui.Gui;

public class Connection implements ActionListener {
    private Gui gui;

    public Connection(Gui gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gui.receiveData(Arrays.asList("Database", "Database", "Database", "Database"));
    }

}
