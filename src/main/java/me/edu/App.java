package me.edu;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import me.edu.ui.Gui;

public class App {
    public static MongoClient client = null;

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

    public static void main(String[] args) {
        new Gui().init();

    }
}
