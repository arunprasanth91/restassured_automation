package org.example.practice;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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

    @Test
    public void postRequestUsingMapsPojo() {
        Location location = new Location();
        location.setLat(-38.383423);
        location.setLng(33.422332);
        MapsPojo mapsPojo = new MapsPojo();
        mapsPojo.setLocation(location);
        mapsPojo.setAccuracy(50);
        mapsPojo.setAddress("29, side layout, cohen 091232");
        mapsPojo.setName("Frontline house123");
        mapsPojo.setPhone_number("(+91) 983 893 3937");
        mapsPojo.setTypes(List.of("turf","gym"));
        mapsPojo.setWebsite("http://google.com");
        mapsPojo.setLanguage("English-US");
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        given().log().all().queryParam("key","qaclick123").header("content-Type","application/json")
                .body(mapsPojo).when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200);
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
