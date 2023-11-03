package me.edu.controller;

import java.util.List;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.MongoCollection;
import me.edu.ui.Gui;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;


/**
 * Crontrols all operations involving the databases, collections and client connection
 */
public class ClientController {
    public static MongoClient client = null;
    public static MongoDatabase targetDatabase = null;
    private static String targetJson = "";
    public static MongoCollection<Document> targetCollection = null; 
    public static Gui gui;

    /**
     * Sets the client (singleton)*/
    public static void setClient(String uri) {
        client = client == null ? MongoClients.create(uri) : client;
    }

    public static void updateServerCollection(String newStringJson){
        // Parse the JSON string into a list of documents
        String[] documentStrings = newStringJson.split(",,");


        for(String docString : documentStrings){
            Document document = Document.parse(docString);
            System.out.println("\n\n\t>>> " + document);

            Object documentId = document.get("_id");

            //the query itself
            Bson query = eq("_id", documentId);

            document.remove("_id");

            ReplaceOptions opts = new ReplaceOptions().upsert(true);

            UpdateResult result = targetCollection.replaceOne(query, document, opts);
            System.out.println("\n\n\tResult: " + result);


        }
    }

    public static List<String> getServerDatabases(){
        MongoIterable<String> dbList = client.listDatabaseNames(); 
        List<String> allDatabases = new ArrayList<>();
        for (String db : dbList)
            allDatabases.add(db);
        
        return allDatabases;
    }

    public static void getServerDocuments(){
      if(targetCollection != null){
        System.out.println("\n\n\tDocuments: " + targetCollection.find());
      }
    }

    public static String prettifyJson(String uglyJson){
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(uglyJson).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    public static String getJsonDocuments(){
        FindIterable<Document> documents = targetCollection.find();
        
        for(Document doc : documents){
            targetJson += prettifyJson(doc.toJson()) + ",,";
        }

        return targetJson;
    }

    public static void setTargetCollection(String collectionName){
      if(targetDatabase != null){
        targetCollection = targetDatabase.getCollection(collectionName);

        gui.updateDocumentsUi();
      }
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
