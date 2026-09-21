package org.example.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.params.CoreConnectionPNames;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class MapsUtils {
    static RequestSpecification reqSpec;

    public RequestSpecification buildMapsReqSpec() throws FileNotFoundException {
        if (reqSpec == null) {
            PrintStream log;
            try {
                log = new PrintStream(new FileOutputStream("logging.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            reqSpec = new RequestSpecBuilder().setBaseUri(PropertiesLoader.getProperty("baseURI"))
                    .addQueryParam("key",PropertiesLoader.getProperty("mapskey"))
                    .setConfig(restAssuredConfigUtil())
                    .addFilter(new ExecutionTimerFilter())
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
            return reqSpec;
        }
        return reqSpec;
    }

    public  ResponseSpecification buildMapsResSpec() {
        ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        return resSpec;
    }

    public String getResponseValue(String response, String key){
        JsonPath jsonPath = new JsonPath(response);
        return jsonPath.get(key);
    }

    public RestAssuredConfig restAssuredConfigUtil() {
        RestAssuredConfig customConfig = RestAssuredConfig.config()
                // Configure timeouts and Apache HttpClient parameters
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000)
                        .setParam(CoreConnectionPNames.SO_TIMEOUT, 10000))
                // Configure redirect behavior
                .redirect(RedirectConfig.redirectConfig()
                        .followRedirects(true)
                        .maxRedirects(5))
                // Configure default encoding
                .encoderConfig(EncoderConfig.encoderConfig()
                        .defaultContentCharset("UTF-8"));

        return customConfig;
    }
}


class ExecutionTimerFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        // 1. Pre-execution logic (Manipulate Request / Start Timers)
        long startTime = System.currentTimeMillis();

        // 2. Delegate execution down the chain
        Response response = ctx.next(requestSpec, responseSpec);

        // 3. Post-execution logic (Inspect Response / Calculate Metrics / Attach Logs)
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("HTTP " + requestSpec.getMethod() + " " + requestSpec.getURI() +
                " executed in " + elapsedTime + " ms");

        return response;
    }

}
