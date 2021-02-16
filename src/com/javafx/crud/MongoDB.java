package com.javafx.crud;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDB {
    public final static String URI_SRV = "mongodb+srv://admin:admin@cluster0.vx6ge.mongodb.net/Database?retryWrites=true&w=majority";
    public final static String HOST = "localhost";
    public final static int PORT = 27017;

    public static MongoClientURI mongoUri;
    public static MongoClient mongoClient;
    public static MongoDatabase mongoDatabase;
    public static MongoCollection<Document> mongoCollection;
    public static MongoCursor<Document> mongoCursor;

    public static void connection() {
        // create a connection to mongodb local server
        mongoClient = new MongoClient(HOST, PORT);

        // create a connection to mongodb cloud server
        // mongoUri = new MongoClientURI(URI_SRV);
        // mongoClient = new MongoClient(mongoUri);

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
