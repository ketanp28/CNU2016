package com.devfactory.cnu.spring;

import com.devfactory.cnu.ketan.spring.controller.Application;
import com.devfactory.cnu.ketan.spring.controller.model.Product;
import com.devfactory.cnu.ketan.spring.controller.model.repository.ProductRepository;
import com.jayway.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.hamcrest.Matchers;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

/**
 * Created by ketanpatil on 18/07/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.JVM)
public class ProductControllerTest {
    private final String productURL = "http://localhost:8080/api/products";
    private final String productIdURL = "http://localhost:8080/api/products/{id}";

    @Autowired
    private ProductRepository productRepo;

    private Product product;
    private int pIdActive,pIdInactive;

    @Before
    public void setUp() {
        // active product
        product = new Product();
        product.setCode("code1");
        product.setActive(1);
        product.setQty(50);
        product.setSell_current(70.0);
        product.setDescription("description1");

        productRepo.save(product);
        pIdActive = product.getId();

        // inactive product
        product = new Product();
        product.setCode("code2");
        product.setActive(0);
        product.setQty(50);

        productRepo.save(product);
        pIdInactive = product.getId();

    }

    @Test
    public void getProducts(){
        Response response = when().
                get(productURL).
                then().
                statusCode(200).
                extract().response();
    }

    @Test
    public void getProductById(){
        Response response = given().
                contentType("application/json").
                when().
                get(productIdURL,pIdActive).
                then().
                statusCode(200).
                body("id", Matchers.is(pIdActive)).
                body("code",Matchers.is("code1")).
                body("description",Matchers.is("description1")).
                extract().response();

        response = given().
                contentType("application/json").
                when().
                get(productIdURL,pIdInactive).
                then().
                statusCode(HttpStatus.NOT_FOUND.value()).
                extract().response();

    }

    @Test
    public void addProduct(){
        product = new Product();
        product.setCode("code3");
        product.setQty(50);
        product.setSell_current(70.0);
        product.setDescription("description3");

        Response response = given().
                contentType("application/json").
                body(product).
                when().
                post(productURL).
                then().
                statusCode(HttpStatus.CREATED.value()).
                body("code",Matchers.is("code3")).
                body("description",Matchers.is("description3")).
                extract().response();

    }

    @Test
    public void patchProduct(){
        product = new Product();
        product.setDescription("description3");

        Response response = given().
                contentType("application/json").
                body(product).
                when().
                patch(productIdURL, pIdActive).
                then().
                statusCode(HttpStatus.OK.value()).
                body("description", Matchers.is("description3")).
                extract().response();

        response = given().
                contentType("application/json").
                body(product).
                when().
                patch(productIdURL, pIdInactive).
                then().
                statusCode(HttpStatus.NOT_FOUND.value()).
                extract().response();

        product.setCode("code4");
        response = given().
                contentType("application/json").
                body(product).
                when().
                patch(productIdURL, pIdActive).
                then().
                statusCode(HttpStatus.OK.value()).
                body("code", Matchers.is("code4")).
                extract().response();

    }

    @Test
    public void updateProduct()
    {
        product = new Product();
        product.setCode("code3");
        product.setDescription("description3");

        Response response = given().
                contentType("application/json").
                body(product).
                when().
                put(productIdURL, pIdActive).
                then().
                statusCode(HttpStatus.OK.value()).
                body("code", Matchers.is("code3")).
                extract().response();

        response = given().
                contentType("application/json").
                body(product).
                when().
                put(productIdURL, pIdInactive).
                then().
                statusCode(HttpStatus.NOT_FOUND.value()).
                extract().response();

    }

    @Test
    public void deleteProduct()
    {
        Response response = given().
                contentType("application/json").
                when().
                delete(productIdURL, pIdActive).
                then().
                statusCode(HttpStatus.OK.value()).
                extract().response();

        response = given().
                contentType("application/json").
                when().
                delete(productIdURL, pIdInactive).
                then().
                statusCode(HttpStatus.NOT_FOUND.value()).
                extract().response();
    }



}
