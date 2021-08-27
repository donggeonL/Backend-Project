package com.amazonaws.lambda.demo;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Hello implements RequestHandler<Map<String, String>, String> {
	
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String handleRequest(Map<String, String> event, Context context) {
    	LambdaLogger logger = context.getLogger();
    	
    	logger.log("\n\nENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
    	logger.log("\n\nCONTEXT:" + gson.toJson(context));
    	logger.log("\n\nEVENT:" + gson.toJson(event));
    	
        // event로 전달된 값을 추출해서 출력
    	String json = gson.toJson(event);
    	JsonParser parser = new JsonParser();
    	JsonElement element = parser.parse(json);
    	
    	String name = element.getAsJsonObject().get("name").getAsString();
    	int age = element.getAsJsonObject().get("age").getAsInt();
    	logger.log("\n\n이름 : " + name);
    	logger.log("\n\n나이 : " + age);

        // TODO: implement your handler
        return "Hello from Lambda!";
    }

}

