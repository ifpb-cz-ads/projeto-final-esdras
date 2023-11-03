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

import static com.mongodb.client.model.Filters.all;
import static com.mongodb.client.model.Filters.eq;


/**
 * Crontrols all operations involving the databases, collections and client connection
 */
public class ClientController {
    public static MongoClient client = null;
    public static MongoDatabase targetDatabase = null;
    private static String targetJson = "";
    public static MongoCollection<Document> targetCollection = null;
    public static String filterKey = null;
    public static String filterValue = null;
    public static Gui gui;

    /**
     * Sets the client (singleton)*/
    public static void setClient(String uri) {
        client = client == null ? MongoClients.create(uri) : client;
    }

    public static void getSpecifDocument(String key, String value){
        FindIterable<Document> doc = targetCollection.find(eq(key, value));
    }

    public static void updateServerCollection(String newStringJson){

        // Parse the JSON string into a list of documents
        String[] documentStrings = newStringJson.split(",,");

        List<Document> allDocuments = new ArrayList<>();
        for(String docString : documentStrings){
            allDocuments.add(Document.parse(docString));
        }

        //comparing with preview state to remove excluded documents
        if(targetCollection.countDocuments() > allDocuments.size()){//document was removed

            for(Document doc : targetCollection.find()){
                boolean found = false;

                for(Document nDoc : allDocuments){
                    if(doc.get("_id").toString().equals(nDoc.get("_id").toString()))
                        found = true;
                }

                if(!found){
                    Bson query = eq("_id", doc.get("_id"));
                    targetCollection.deleteOne(query);
                }
            }

        } else if(targetCollection.countDocuments() < allDocuments.size()){//document was added
            for(Document doc : allDocuments){
                boolean found = false;

                System.out.println("\n\n\tDoc" + doc.get("_id"));


                if(doc.get("_id") == null){
                    targetCollection.insertOne(doc);
                    System.out.println("\n\n\tDocument created!");
                }
            }
        }

        //updating all documents
        for(Document document : allDocuments){

            Object documentId = document.get("_id");

            //the query itself
            Bson query = eq("_id", documentId);

            document.remove("_id");

            ReplaceOptions opts = new ReplaceOptions().upsert(true);

            UpdateResult result = targetCollection.replaceOne(query, document, opts);
            System.out.println("\n\n\tResult: " + result);


        }

        gui.updateDocumentsUi();
    }

    public static String prettifyJson(String uglyJson){
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(uglyJson).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    public static String getJsonDocuments(){
        targetJson = "";
        FindIterable<Document> documents;

        //getting the right documents
        if(filterKey == null || filterValue == null){
            System.out.println("\n\n\tnot filters");
            documents = targetCollection.find();
        }
        else{
            System.out.println("\n\n\tthere is filter");
            documents = targetCollection.find(eq(filterKey, filterValue));
        }

        
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

    public static List<String> getServerDatabases(){
        MongoIterable<String> dbList = client.listDatabaseNames();
        List<String> allDatabases = new ArrayList<>();
        for (String db : dbList)
            allDatabases.add(db);

        return allDatabases;
    }

    /**
     * Creates a new database if the client is not null*/
    public static MongoDatabase createDatabase(String databaseName) {
        if (client != null && !databaseName.isEmpty()) {
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
