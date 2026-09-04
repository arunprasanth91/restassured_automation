package org.example.utils;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import lombok.Builder;
import lombok.Data;


public class ThreadLocalInstance {

	private static final ThreadLocal<RequestSpecification> requestSpecificationThreadLocal = new ThreadLocal<>();

	public static void initRequestSpec(RequestConfig config){
		RequestSpecification reqSpec = RestAssured.given();
		if (config.getBaseUrl() != null) reqSpec.baseUri(config.getBaseUrl());
		if (config.getBasePath() != null) reqSpec.basePath(config.getBasePath());
		if (config.getHeaders() != null) reqSpec.headers(config.getHeaders());
		if (config.getQueryParams() != null) reqSpec.queryParams(config.getQueryParams());
		if (config.getBody() != null) reqSpec.body(config.getBody());
		requestSpecificationThreadLocal.set(reqSpec);
	}

	public static RequestSpecification getRequestSpec() {
		if (requestSpecificationThreadLocal.get() == null) {
			requestSpecificationThreadLocal.set(RestAssured.given());
		}
		return requestSpecificationThreadLocal.get();
	}

	public static void unload(){
		requestSpecificationThreadLocal.remove();
	}

}
