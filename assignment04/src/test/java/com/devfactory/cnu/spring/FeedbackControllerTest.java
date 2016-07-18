package com.devfactory.cnu.spring;
import com.devfactory.cnu.ketan.spring.controller.model.Feedback;
import com.jayway.restassured.response.Response;

import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by ketanpatil on 18/07/16.
 */
public class FeedbackControllerTest {
    private final String contactusURL = "http://localhost:8080/api/contactus";

    @Test
    public void addFeedback(){

        Feedback feedback1 = new Feedback("feedback info");

        Response responce = given().contentType("application/json").body(feedback1).
                    when().post(contactusURL).
                    then().statusCode(200).
                    extract().response();

    }


}
