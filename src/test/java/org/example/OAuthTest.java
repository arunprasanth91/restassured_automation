package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class OAuthTest {

    private static String access_token = null;

    @Test
    public void getSessionId(){
        String response = given()
                .formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParam("scope","trust").log().all()
                .when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").then().extract().response().asString();
        JsonPath jsonPath = new JsonPath(response);
        access_token = jsonPath.getString("access_token");
        System.out.println(access_token);
    }


    @Test(dependsOnMethods = "getSessionId")
    public void getCourseDetails() {
        String r2=    given()

                .queryParams("access_token", access_token)

                .when()

                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")

                .asString();

        System.out.println(r2);
    }
}
