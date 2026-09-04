package org.example.practice;

import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class SchemaTest {

	@Test
	public void testExternalSchema() {
		File schemaFile = new File("path/to/your/external-schema.json");

		given()
				.baseUri("https://jsonplaceholder.typicode.com")
				.when()
				.get("/users/1")
				.then()
				.statusCode(200)
				.body(matchesJsonSchema(schemaFile));
	}
}
