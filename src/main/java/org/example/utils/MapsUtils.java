package org.example.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class MapsUtils {
    static RequestSpecification reqSpec;

    public RequestSpecification buildMapsReqSpec() throws FileNotFoundException {
        if (reqSpec == null) {
            PrintStream log;
            try {
                log = new PrintStream(new FileOutputStream("logging.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            reqSpec = new RequestSpecBuilder().setBaseUri(PropertiesLoader.getProperty("baseURI"))
                    .addQueryParam("key",PropertiesLoader.getProperty("mapskey"))
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
            return reqSpec;
        }
        return reqSpec;
    }

    public  ResponseSpecification buildMapsResSpec() {
        ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        return resSpec;
    }

    public String getResponseValue(String response, String key){
        JsonPath jsonPath = new JsonPath(response);
        return jsonPath.get(key);
    }
}
