package com.mongodb.m101j.homework;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;

/**
 * Created by ariielm on 8/16/16.
 */
public class Homework23Solution {

    public static void main(final String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase numbersDB = client.getDatabase("students");
        MongoCollection<Document> grades = numbersDB.getCollection("grades");

        MongoCursor<Document> cursor = grades.find(eq("type", "homework"))
                .sort(ascending("student_id", "score")).iterator();

        Object studentId = -1;
        try {
            while (cursor.hasNext()) {
                Document entry = cursor.next();
                if (!entry.get("student_id").equals(studentId)) {
                    System.out.println("Removing: " + entry);
                    Object id = entry.get("_id");
                    grades.deleteOne(eq("_id", id));

                }
                studentId = entry.get("student_id");
            }
        } finally {
            cursor.close();
        }
    }

}
