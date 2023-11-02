package me.edu.controller;

import java.util.*;

/**
 * Handles all the operations related to the data itself (databases, collections, etc)*/
public class DataController {
    private static List<String> databases = new ArrayList<>(); 
    private static List<String> collections = new ArrayList<>();
    private static String targetDatabase = null;

    public static List<String> getDatabases(){
      return new ArrayList<>(databases);
    }
    
    public static List<String> getCollections(){
      return new ArrayList<>(collections);
    }
    
    public static String getTargetDatabase(){
      return targetDatabase;
    }

    public static List<String>  updateDatabases(List<String> dbs) {
        databases = new ArrayList<>();
        databases.addAll(dbs);

        return new ArrayList<>(databases);
    }

    public static List<String> addDatabase(String db){
      databases.add(db);
      return new ArrayList<>(databases);
    }

    public static List<String> updateCollections(List<String> colls){
      collections = new ArrayList<>();
      collections.addAll(colls);

      return new ArrayList<>(collections);
    }

    public static List<String> addCollection(String coll){
      collections.add(coll);

      return new ArrayList<>(collections);
    }
    
    public static String setTargetDatabase(String db){
      targetDatabase = db;
      return targetDatabase;
    }

}
