package org.example.practice;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.List;

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


    @Test(enabled = false, dependsOnMethods = "getSessionId")
    public void getCourseDetails() {
        String r2=    given()

                .queryParams("access_token", access_token)

                .when()

                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")

                .asString();

        System.out.println(r2);
    }


    @Test(dependsOnMethods = "getSessionId")
    public void getCourseDetailsUsingDeserialization() {
        CoursesPojo coursesPojo = given()

                .queryParams("access_token", access_token)

                .when()

                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")

                .as(CoursesPojo.class);

        System.out.printf(coursesPojo.getInstructor()+" , "+coursesPojo.getExpertise());

        // find coursetitle rest assured and get the price

        Courses courses = coursesPojo.getCoursesList();
        List<API> apiList = courses.getApi();
        for (API api : apiList){
            if (api.getCourseTitle().contains("Rest Assured")){
                System.out.println(api.getCourseTitle() + " price is: "+api.getPrice());
                break;
            }
        }
    }
 }
