package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraAPITest {

    @Test
    public void createIssueAddAttachment() {
        // Create issue
        RestAssured.baseURI = "https://arunprasanthp.atlassian.net/";
        String createIssueResponse = given().header("Content-Type", "application/json")
                .header("Authorization", "Basic YXJ1bnByYXNhbnRocEB5YWhvby5jb206QVRBVFQzeEZmR0Ywa2FDUUI1OXdOVWc5OTk1YnNFVmZHZl9JalVfamtFdllEX1A2el9maDVKTGlHaW5PTlRYZjEtUTA4S2x2WXczTW5OWXZZYVE4c2hmbzRrS0Jkc0dKaHNncnlaclBtQ3U3bkxVbzU4WHc2eGc4SnVCRk5FZHJ0emFJUzJkd291Rm5kaHpsZHJtR0I1czJ2cWtySEFEaGJma0ttVk5XTFkxOURwNTFNMnN4WFBrPUM5NTI0NUUz")
                .body("{\n" + "    \"fields\": {\n" + "       \"project\":\n" + "       {\n" + "          \"key\": \"DEV\"\n" + "       },\n" + "       \"summary\": \"Website items are not working- automation Rest Assured\",\n" + "       \"issuetype\": {\n" + "          \"name\": \"Bug\"\n" + "       }\n" + "   }\n" + "}")
                .log().all().post("rest/api/3/issue").then().log().all().assertThat().statusCode(201).extract().response().asString();
        JsonPath js = new JsonPath(createIssueResponse);
        String issueId = js.getString("id");
        System.out.println(issueId);
        //Add attachment
        given().pathParam("key", issueId).header("X-Atlassian-Token", "no-check").header("Authorization", "Basic YXJ1bnByYXNhbnRocEB5YWhvby5jb206QVRBVFQzeEZmR0Ywa2FDUUI1OXdOVWc5OTk1YnNFVmZHZl9JalVfamtFdllEX1A2el9maDVKTGlHaW5PTlRYZjEtUTA4S2x2WXczTW5OWXZZYVE4c2hmbzRrS0Jkc0dKaHNncnlaclBtQ3U3bkxVbzU4WHc2eGc4SnVCRk5FZHJ0emFJUzJkd291Rm5kaHpsZHJtR0I1czJ2cWtySEFEaGJma0ttVk5XTFkxOURwNTFNMnN4WFBrPUM5NTI0NUUz")
                .multiPart("file", new File("screenshot.png")).log().all()
                .post("rest/api/3/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);

    }
}
