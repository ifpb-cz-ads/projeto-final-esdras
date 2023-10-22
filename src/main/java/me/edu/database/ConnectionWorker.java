package me.edu.database;

import java.util.Arrays;
import java.util.List;

import javax.swing.SwingWorker;

public class ConnectionWorker extends SwingWorker<List<String>, Void> {

    @Override
    protected List<String> doInBackground() throws Exception {
        return Arrays.asList("Database", "Database", "Database", "Database");
    }

    @Override
    public void done() {
        System.out.println("The worker done the job!");
    }

}
