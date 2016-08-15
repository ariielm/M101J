package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;

public class App  {

    public static void main( String[] args ) {
        //MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));

        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).build();
        MongoClient client = new MongoClient(new ServerAddress(), options);

        MongoDatabase db = client.getDatabase("test").withReadPreference(ReadPreference.secondary());

        MongoCollection<BsonDocument> coll = db.getCollection("test", BsonDocument.class);
    }

}
