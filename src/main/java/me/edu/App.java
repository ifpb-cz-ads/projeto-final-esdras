package me.edu;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

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

    public static void main(String[] args) {
        new Gui().init();

    }
}
