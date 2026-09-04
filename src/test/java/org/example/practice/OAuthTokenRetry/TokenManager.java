package org.example.practice.OAuthTokenRetry;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TokenManager {

	private String currentToken;
	private final String tokenEndpoint;
	private final String clientId;
	private final String clientSecret;

	public TokenManager(String tokenEndpoint, String clientId, String clientSecret) {
		this.tokenEndpoint = tokenEndpoint;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	/**
	 * Returns the current token, or fetches a new one if it doesn't exist yet.
	 */
	public synchronized String getCurrentToken() {
		if (currentToken == null) {
			return refreshToken();
		}
		return currentToken;
	}

	/**
	 * Executes the Client Credentials flow to fetch a fresh token.
	 */
	public synchronized String refreshToken() {
		System.out.println("Fetching new OAuth 2.0 token...");

		Response response = RestAssured.given()
				// Many OAuth providers expect the credentials as form parameters
				.formParam("grant_type", "client_credentials")
				.formParam("client_id", clientId)
				.formParam("client_secret", clientSecret)
				// .auth().preemptive().basic(clientId, clientSecret) // Use this instead if your API requires Basic Auth
				.when()
				.post(tokenEndpoint);

		if (response.statusCode() != 200) {
			throw new RuntimeException("Failed to fetch token. Status code: "
					+ response.statusCode() + " Body: " + response.getBody().asString());
		}

		// Extract the access_token from the JSON response
		this.currentToken = response.jsonPath().getString("access_token");

		return this.currentToken;
	}
}
