package me.edu;

import java.util.Iterator;

import com.mongodb.MongoClient;

import me.edu.database.MClient;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        MongoClient client = MClient.get();
        System.out.println("Connected to mongodb");

        Iterator<String> i = client.listDatabaseNames().iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }

        client.close();
    }
}
