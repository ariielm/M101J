package com.mongodb.m101j.homework;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.m101j.util.Helpers;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.m101j.util.Helpers.printJson;

/**
 * Created by ariielm on 8/16/16.
 */
public class Homework23 {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("students");
        MongoCollection<Document> collection = database.getCollection("grades");

        MongoCursor<Document> removableDocs = collection.find(new Document("type", "homework"))
                .sort(Sorts.ascending("student_id", "score")).iterator();

        Document cur = removableDocs.next();

        ObjectId id;
        ObjectId removableId = cur.getObjectId("_id");
        Integer studentId;
        Integer previousStudentId = cur.getInteger("student_id");
        double score;
        double lowerScore = cur.getDouble("score");

        try {
            while (removableDocs.hasNext()) {
                cur = removableDocs.next();

                id = cur.getObjectId("_id");
                studentId = cur.getInteger("student_id");
                score = cur.getDouble("score");

                if(studentId.equals(previousStudentId) && removableDocs.hasNext()) {
                    if (score < lowerScore) {
                        lowerScore = score;
                        removableId = id;
                    }
                } else {
                    collection.deleteOne(eq("_id", removableId));
                    removableId = cur.getObjectId("_id");
                    lowerScore = cur.getDouble("score");
                }

                previousStudentId = studentId;
            }
        } finally {
            removableDocs.close();
        }

        System.out.println(collection.count());

    }
}
