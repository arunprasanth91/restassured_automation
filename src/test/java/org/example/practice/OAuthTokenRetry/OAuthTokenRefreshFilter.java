package org.example.practice.OAuthTokenRetry;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class OAuthTokenRefreshFilter implements Filter {

	private TokenManager tokenManager; // Your custom class that handles the OAuth API calls

	public OAuthTokenRefreshFilter(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}
	@Override
	public Response filter(FilterableRequestSpecification requestSpec,
	                       FilterableResponseSpecification responseSpec,
	                       FilterContext ctx) {

		// 1. Inject the current token into the request
		requestSpec.header("Authorization", "Bearer " + tokenManager.getCurrentToken());

		// 2. Execute the request
		Response response = ctx.next(requestSpec, responseSpec);

		// 3. Check for expiration (401 Unauthorized)
		if (response.statusCode() == 401) {
			System.out.println("Token expired mid-test. Refreshing...");

			// Fetch a new token using the refresh_token or client credentials
			String newToken = tokenManager.refreshToken();

			// Update the request with the new token
			requestSpec.replaceHeader("Authorization", "Bearer " + newToken);

			// 4. Replay the request with the new token
			response = ctx.next(requestSpec, responseSpec);
		}

		return response;
	}
}
