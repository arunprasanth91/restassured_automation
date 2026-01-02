package org.example.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.utils.APIResources;
import org.example.utils.MapsUtils;
import org.example.testdata.MapsTestData;

import java.io.FileNotFoundException;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class MapsAPI_StepDef extends MapsUtils {
    RequestSpecification mapsReq;
    Response response;
    MapsTestData maps = new MapsTestData();
    static String place_id;

    @Given("Add place payload with values {string} {string} {string} {string} {string}")
    public void add_place_payload_with_values(String address, String name, String phone_number, String website, String language) throws FileNotFoundException {
        mapsReq = given().spec(buildMapsReqSpec()).body(maps.mapsTestData(address,name,phone_number,website,language));
    }
    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String httpMethod) {
        APIResources apiResources = APIResources.valueOf(resource);
        if (httpMethod.equalsIgnoreCase("post")){
            response = mapsReq.when().post(apiResources.getResource()).then().spec(buildMapsResSpec()).extract().response();
        }else if (httpMethod.equalsIgnoreCase("get")){
            response = mapsReq.when().get(apiResources.getResource()).then().spec(buildMapsResSpec()).extract().response();
        }else if (httpMethod.equalsIgnoreCase("delete")){
            response = mapsReq.when().delete(apiResources.getResource()).then().spec(buildMapsResSpec()).extract().response();
        }
    }
    @Then("the API call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(int statusCode) {
        assertEquals(response.getStatusCode(),statusCode);

    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String expectedValue) {
        assertEquals(getResponseValue(response.asString(),keyValue).toString(),expectedValue);
    }

    @Then("verify place_Id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedName, String resourceName) throws FileNotFoundException {
        place_id = getResponseValue(response.asString(),"place_id");
        mapsReq = given().spec(buildMapsReqSpec()).queryParam("place_id",place_id);
        user_calls_with_http_request(resourceName,"GET");
        String actualName = getResponseValue(response.asString(),"name");
        assertEquals(actualName,expectedName);
    }

    @Given("DeletePlace payload")
    public void delete_place_payload() throws FileNotFoundException {
        mapsReq = given().spec(buildMapsReqSpec()).body(maps.deletePlacePayload(place_id));
    }

}
