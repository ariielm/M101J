package com.mongodb.m101j.homework;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.m101j.util.Helpers;
import org.bson.Document;

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

        String id;
        String removableId = "";
        String studentId;
        String previousStudentId = "";
        double score;
        double lowerScore = 100;

        try {
            while (removableDocs.hasNext()) {
                Document cur = removableDocs.next();

                id = cur.getString("_id");
                studentId = cur.getString("student_id");
                score = cur.getDouble("score");

                if(studentId.equals(previousStudentId)) {
                    if (score < lowerScore) {
                        lowerScore = score;
                        removableId = id;
                    }
                } else {
                    collection.deleteOne(eq("_id", removableId));
                    removableId = "";
                    lowerScore = 100;
                }

                previousStudentId = studentId;
                //printJson(cur);
            }
        } finally {
            removableDocs.close();
        }

        System.out.println(collection.count());

    }
}
