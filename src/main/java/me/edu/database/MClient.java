package me.edu.database;

import com.mongodb.MongoClient;

public class MClient {
    private static MongoClient client = null;

    private MClient() {
    }

    public static MongoClient get() {
        if (client == null)
            client = new MongoClient("localhost", 27017);

        return client;
    }

    public static void close() {
        client.close();
    }
}
