package me.edu.database;

import com.mongodb.MongoClient;

public class MClient {
    private static MongoClient client = null;

    private MClient() {
    }

    public static MongoClient get(String uri) {
        if (client == null)
            client = new MongoClient(uri);

        return client;
    }

    public static void close() {
        client.close();
    }
}
