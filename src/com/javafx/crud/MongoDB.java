package com.javafx.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDB {
    public final static String HOST = "localhost";
    public final static int PORT = 27017;

    public static MongoClient mongoClient;
    public static MongoDatabase mongoDatabase;
    public static MongoCollection<Document> mongoCollection;
    public static MongoCursor<Document> mongoCursor;

    public static void connection() {
        // create a connection to mongodb server
        mongoClient = new MongoClient(HOST, PORT);

        // create a database name
        mongoDatabase = mongoClient.getDatabase("Database");

        // create a collection
        mongoCollection = mongoDatabase.getCollection("Persons");
    }

    public static MongoCursor<Document> findAllItems() {
        // calls the find all methods from the mongodb database
        // all the documents which are returned are saved in a virtual cursor
        mongoCursor = MongoDB.mongoCollection.find().iterator();
        return mongoCursor;
    }
}
