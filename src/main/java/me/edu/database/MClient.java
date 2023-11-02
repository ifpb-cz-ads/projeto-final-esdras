package me.edu.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MClient {
    private static MongoClient client = null;

    private MClient() {
    }

    public static MongoClient get(String uri) {
        if (client == null)
            client = MongoClients.create(uri);

        return client;
    }

    public static void close() {
        client.close();
    }
}
