package me.edu.controller;

import java.util.List;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import me.edu.ui.Gui;


/**
 * Crontrols all operations involving the databases, collections and client connection
 */
public class ClientController {
    public static MongoClient client = null;
    public static MongoDatabase targetDatabase = null;
    public static Gui gui;

    /**
     * Sets the client (singleton)*/
    public static void setClient(String uri) {
        
        client = client == null ? MongoClients.create(uri) : client;
    }

    public static List<String> getServerDatabases(){
        MongoIterable<String> dbList = client.listDatabaseNames(); 
        List<String> allDatabases = new ArrayList<>();
        for (String db : dbList)
            allDatabases.add(db);
        
        return allDatabases;
    }

    /**
     * Returns the client*/
    public static MongoClient getClient() {
        return client;
    }

    /**
     * Creates a new database if the client is not null*/
    public static MongoDatabase createDatabase(String databaseName) {
        if (client != null && databaseName.length() > 0) {
            MongoDatabase db = client.getDatabase(databaseName);
            db.getCollection("test").insertOne(new Document("createdBy", "esdrasSilva"));
            
            DataController.addDatabase(db.getName());
            gui.updatedatabasesUi();

            return db;
        }

        return null;
    }

    /**
     * Deletes a database*/
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

    public static void loadCollections() {
        MongoIterable<String> collections = targetDatabase.listCollectionNames();
        List<String> collsList = new ArrayList<>();

        for (String colName : collections) {
            collsList.add(colName);
        }
        
        DataController.updateCollections(collsList);
        gui.updateCollectionsUi();
    }

    public static void connectToDatabase(String databaseName) {

        if (client != null) {
            targetDatabase = client.getDatabase(databaseName);
            loadCollections();
        }
    }
}
