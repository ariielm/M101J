package com.mongodb;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created by ariielm on 8/5/16.
 */
public class SparkRoute {

    public static void main(String[] args) {
        Spark.get(new Route("/") {
            public Object handle(Request request, Response response) {
                return "Hello World\n";
            }
        });

        Spark.get(new Route("/test") {
            public Object handle(Request request, Response response) {
                return "This is a page test\n";
            }
        });

        Spark.get(new Route("/echo/:thing") {
            public Object handle(Request request, Response response) {
                return request.params("thing");
            }
        });
    }
}
