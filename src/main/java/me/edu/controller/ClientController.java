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

    public static void getSpecifDocum111ent(String key, String value){
        FindIterable<Document> doc = targetCollection.find(eq(key, value));
    }


    /**
     * Updates the collection with the new json*/
    public static void updateServerCollection(String newStringJson){

        if(filterValue != null && filterKey != null){
            //update specific document
            if(!newStringJson.isEmpty() || !newStringJson.isBlank()){
                if(newStringJson.charAt(0) == '*'){//document was marked to be removed
                    Document doc = Document.parse(newStringJson.replace("*", ""));
                    targetCollection.deleteOne(eq("_id", doc.get("_id")));

                } else {
                    Document doc = Document.parse(newStringJson.replace(",,", ""));
                    targetCollection.replaceOne(eq("_id", doc.get("_id")), doc, new ReplaceOptions().upsert(true));
                }
            }

        } else {
            // Parse the JSON string into a list of documents
            String[] documentStrings = newStringJson.split(",,");

            List<Document> allDocuments = new ArrayList<>();
            for(String docString : documentStrings){
                allDocuments.add(Document.parse(docString));
            }

            //comparing with preview state to remove excluded documents
            if(targetCollection.countDocuments() > allDocuments.size()){//document was removed

                //finding the missing document to remove
                for(Document doc : targetCollection.find()){
                    boolean found = false;

                    for(Document nDoc : allDocuments){
                        if(doc.get("_id").toString().equals(nDoc.get("_id").toString()))
                            found = true;
                    }

                    if(!found){ //means it is the missing document
                        Bson query = eq("_id", doc.get("_id"));
                        targetCollection.deleteOne(query);
                    }
                }

            } else if(targetCollection.countDocuments() < allDocuments.size()){//document was added
                for(Document doc : allDocuments){

                    if(doc.get("_id") == null){
                        targetCollection.insertOne(doc);
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
            }
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
            documents = targetCollection.find();
        }
        else{
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

    public static void createCollection(String collectionName){
        targetDatabase.createCollection(collectionName);
        DataController.addCollection(collectionName);
        gui.updateCollectionsUi();
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

            DataController.updateDatabases(stringDbs);

            return stringDbs;
        }
        gui.updatedatabasesUi();
        return null;
    }

    public static void deleteCollection(String collectionName){
        MongoCollection<Document> collection = targetDatabase.getCollection(collectionName);
        collection.drop();

        List<String> allCollections = new ArrayList<>();
        for(String coll : targetDatabase.listCollectionNames()){
            allCollections.add(coll);
        }

        DataController.updateCollections(allCollections);
        gui.updateCollectionsUi();
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
