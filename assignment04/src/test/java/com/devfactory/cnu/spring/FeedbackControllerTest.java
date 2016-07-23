package com.devfactory.cnu.spring;

import org.springframework.http.HttpStatus;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by ketanpatil on 18/07/16.
 */
public class FeedbackControllerTest {
    private final String contactusURL = "http://localhost:8080/api/contactus";

    @Test
    public void addFeedback(){

        given().
                contentType("application/json")
                .body("")
                .when()
                .post(contactusURL)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        given().
                contentType("application/json")
                .body("good feedback")
                .when()
                .post(contactusURL)
                .then()
                .statusCode(HttpStatus.OK.value());

    }


}
