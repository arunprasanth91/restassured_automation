package org.example.practice.ecom;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.runner.Request;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcommTest {
    String sessionId, userId, productId = null;
    RequestSpecification requestSpecification =
            new RequestSpecBuilder().setBaseUri("https://www.rahulshettyacademy.com/").setContentType(ContentType.JSON).build();
    LoginReqPojo loginReqPojo = new LoginReqPojo();

    @Test
    public void loginTest() {
        loginReqPojo.setUserEmail("seleniumpractice@wd.com");
        loginReqPojo.setUserPassword("Password@123");
        RequestSpecification loginSpec = given().spec(requestSpecification).log().all().body(loginReqPojo);
        org.example.practice.ecom.LoginResponsePojo loginResponsePojo = loginSpec.when().post("api/ecom/auth/login").then().log().all().statusCode(200).extract().response()
                .as(LoginResponsePojo.class);
        sessionId = loginResponsePojo.getToken();
        userId = loginResponsePojo.getUserId();
        System.out.println(sessionId);
        System.out.println(userId);
    }


    @Test(dependsOnMethods = "loginTest")
    public void createProductTest() {
        RequestSpecification reqSpecAuthorization = new RequestSpecBuilder().setBaseUri("https://www.rahulshettyacademy.com/").addHeader("authorization", sessionId)
                .build();
        // form params are added as param
        RequestSpecification reqAddProduct = given().spec(reqSpecAuthorization).log().all().param("productName", "Women's T-Shirt").param("productAddedBy", userId)
                .param("productCategory", "fashion").param("productSubCategory", "shirts").param("productPrice", "11500")
                .param("productDescription", "Adidas Tshirt").param("productFor", "Women").multiPart("productImage", new File("adidas_orginals.png"));
        ProductPojo productPojo = reqAddProduct.when().post("api/ecom/product/add-product").then().log().all().extract().response().as(ProductPojo.class);
        productId = productPojo.getProductId();
        System.out.println(productId);
    }


    @Test(dependsOnMethods = {"loginTest", "createProductTest"})
    public void createOrder() {
        Orders orders = new Orders();
        Order order = new Order();
        order.setCountry("India");
        order.setProductOrderedId(productId);
        orders.setOrders(List.of(order));
        RequestSpecification orderReqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", sessionId)
                .setContentType(ContentType.JSON).build();

        RequestSpecification createOrderSpec = given().spec(orderReqSpec).log().all().body(orders);
        Response orderResponse = createOrderSpec.when().post("api/ecom/order/create-order").then().log().all().extract().response();
        System.out.println(orderResponse.asString());
    }


    @Test(dependsOnMethods = {"loginTest", "createProductTest"})
    public void deleteProduct() {
        RequestSpecification deleteReqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", sessionId).setContentType(ContentType.JSON).build();
        RequestSpecification deleteSpec = given().spec(deleteReqSpec).log().all().pathParam("productId", productId);
        String deleteResponse = deleteSpec.when().delete("api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
        System.out.println(deleteResponse);
    }
}

