package org.example;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MapsTest {

    String place_id = null;


    @Test(enabled = false) // vanilla test
    public void postRequest() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        given().log().all().queryParam("key","qaclick123").header("content-Type","application/json")
                .body("{\n" +
                        "  \"location\": {\n" +
                        "    \"lat\": -38.383494,\n" +
                        "    \"lng\": 33.427362\n" +
                        "  },\n" +
                        "  \"accuracy\": 50,\n" +
                        "  \"name\": \"Frontline house\",\n" +
                        "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                        "  \"address\": \"29, side layout, cohen 09\",\n" +
                        "  \"types\": [\n" +
                        "    \"shoe park\",\n" +
                        "    \"shop\"\n" +
                        "  ],\n" +
                        "  \"website\": \"http://google.com\",\n" +
                        "  \"language\": \"French-IN\"\n" +
                        "}\n").when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200);
    }


    @Test(enabled = false) // response validation using body method
    public void postRequestBodyValidation() throws IOException {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
       given().log().all().queryParam("key","qaclick123").header("content-Type","application/json")
                .body(Files.readString(Path.of("src/test/resources/Addplace.json"))).when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("Server","Apache/2.4.52 (Ubuntu)");

    }


    @Test // fetch placeid using JsonPath
    public void placeAPI_e2eTest() throws IOException {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        // create place
        String response = given().log().all().queryParam("key","qaclick123").header("content-Type","application/json")
                .body(Files.readString(Path.of("src/test/resources/Addplace.json"))).when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("Server","Apache/2.4.52 (Ubuntu)").extract().response().asString();
        JsonPath jsonPath = new JsonPath(response);
        place_id = jsonPath.get("place_id");
        System.out.println(place_id);

        // update place

        String updateplaceJson = Files.readString(Path.of("src/test/resources/updateplace.json"));
        updateplaceJson = updateplaceJson.replace("?",place_id);
        given().log().all().queryParam("key","qaclick123").header("content-Type","application/json")
                .body(updateplaceJson).when().put("/maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
                .body("msg",equalTo("Address successfully updated"));


        // get place
        String get_respone  = given().log().all().queryParam("key","qaclick123").queryParam("place_id",place_id)
                .when().get("/maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response().asString();
        jsonPath = new JsonPath(get_respone);
        Assert.assertEquals(jsonPath.get("address"),"70 winter walk, USA");

    }

}
