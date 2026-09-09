import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@WireMockTest(httpPort = 8089) // Starts WireMock on port 8089
public class WireMockRestAssuredTest {

    @BeforeAll
    public static void setup() {
        // Configure REST Assured to point to your Application Under Test (AUT)
        // Note: Your AUT should be configured to route its downstream calls to localhost:8089
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080; 
    }

    @Test
    public void testGetUserDetailsWithMockedDependency() {
        // 1. Stub the downstream dependency via WireMock
        stubFor(get(urlEqualTo("/external/users/123"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 123, \"status\": \"ACTIVE\"}")));

        // 2. Call your Application Under Test using REST Assured
        given()
            .pathParam("id", "123")
        .when()
            .get("/api/v1/accounts/{id}")
        .then()
            .statusCode(200)
            .body("userStatus", equalTo("ACTIVE")); // Verified system behavior based on stub
    }
}
