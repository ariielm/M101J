package com.mongodb;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created by ariielm on 6/7/16.
 */
public class HelloWorldSparkStyle {

    public static void main(String[] args) {
        Spark.get("/", new Route() {
            public Object handle(Request request, Response response) {
                return "Hello World From Spark";
            }
        });
    }

}
