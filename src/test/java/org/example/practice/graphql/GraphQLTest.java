package org.example.practice.graphql;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;

public class GraphQLTest {

    @Test
    public void test1() throws IOException {
        String response = given().log().all().header("content-Type","application/json")
                .body(Files.readString(Path.of("payload.json"))).when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();
        System.out.println(response);
        JsonPath jsonPath = new JsonPath(response);
        System.out.println("Name = "+jsonPath.get("data.character.name"));
    }
}
