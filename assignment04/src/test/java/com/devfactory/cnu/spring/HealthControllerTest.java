package com.devfactory.cnu.spring;

import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by ketanpatil on 18/07/16.
 */
public class HealthControllerTest {

    @Test
    public void testHealth() {
        Response response = given().
                contentType("application/json").
                when().
                get("/api/health").
                then().
                statusCode(HttpStatus.OK.value()).
                extract().response();
    }
}
