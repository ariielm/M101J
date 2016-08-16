package com.mongodb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ariielm on 8/5/16.
 */
public class SparkFormHandling {

    public static void main(String[] args) {

        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(SparkFormHandling.class, "/");

        Spark.get(new Route("/") {
            public Object handle(Request request, Response response) {
                Map<String, Object> fruitsMap = new HashMap<String, Object>();
                fruitsMap.put("fruits", Arrays.asList("apple", "orange", "banana", "peach"));

                try {
                    Template fruitPickerTemplate = configuration.getTemplate("fruitPicker.ftl");
                    StringWriter writer = new StringWriter();
                    fruitPickerTemplate.process(fruitsMap, writer);
                    return writer;
                } catch (Exception e) {
                    halt(500);
                    e.printStackTrace();
                    return null;
                }
            }
        });

        Spark.post(new Route("/favorite_fruit") {
            public Object handle(Request request, Response response) {
                final String fruit = request.queryParams("fruit");
                if (fruit == null) {
                    return "Why don't you pick one?";
                } else {
                    return "Your favorite fruit is " + fruit;
                }
            }
        });

    }

}
