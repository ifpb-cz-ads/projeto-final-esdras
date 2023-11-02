package me.edu.controller;

import java.util.List;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import me.edu.ui.Gui;


public class ClientController {
    public static MongoClient client = null;
    public static MongoDatabase targetDatabase = null;
    public static Gui gui;

    public static void setClient(MongoClient clt) {
        client = clt;
    }

    public static MongoClient getClient() {
        return client;
    }

    public static MongoDatabase createDatabase(String databaseName) {
        if (client != null && databaseName.length() > 0) {
            MongoDatabase db = client.getDatabase(databaseName);
            db.getCollection("test").insertOne(new Document("createdBy", "esdrasSilva"));

            return db;
        }

        System.out.println("Database not created!");
        return null;
    }

    public static List<String> deleteDatabase(String databaseName) {
        MongoDatabase db = client.getDatabase(databaseName);
        if (db != null) {
            db.drop();
            MongoIterable<String> dbs = client.listDatabaseNames();
            List<String> stringDbs = new ArrayList<>();

            for (String dbName : dbs)
                stringDbs.add(dbName);

            return stringDbs;
        }

        return null;
    }

    public static void connectToDatabase(String databaseName) {

        if (client != null) {
            targetDatabase = client.getDatabase(databaseName);
            System.out.println("\n\n\tDatabase: " + targetDatabase.getName() + "\n\n");
            gui.loadCollections();
        }
    }
}
