package me.edu;


import me.edu.controller.ClientController;
import me.edu.ui.Gui;

public class App {
    public static Gui gui = null;
    
    public static void main(String[] args) {
        gui = new Gui();
        ClientController.gui = gui;

        gui.init();
    }
}
