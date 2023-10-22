package me.edu;

import com.mongodb.MongoClient;

import me.edu.database.MClient;
import me.edu.ui.Gui;

public class App {
    public static MongoClient client = null;

    public static void setClient(MongoClient clt) {
        client = clt;
    }

    public static MongoClient getClient() {
        return client;
    }

    public static void main(String[] args) {
        new Gui().init();

    }
}
