package org.example.practice.OAuthTokenRetry;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TokenRetryTest {

	@BeforeMethod
	public void procureToken() {
		TokenManager myTokenManager = new TokenManager("https://token.com","client-123", "adiasdj12331fsdfsddas");
		RestAssured.filters(new OAuthTokenRefreshFilter(myTokenManager));
	}
	@Test
	public void test(){

// Now tests run normally. If the token expires here, the filter handles it seamlessly.
		given()
				.when()
				.get("/api/secure/data")
				.then()
				.statusCode(200);
	}
}
