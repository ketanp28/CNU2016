package com.devfactory.cnu.spring;

import com.devfactory.cnu.ketan.spring.controller.Application;
import com.devfactory.cnu.ketan.spring.controller.model.OrderDetails;
import com.devfactory.cnu.ketan.spring.controller.model.Orders;
import com.devfactory.cnu.ketan.spring.controller.model.Product;
import com.devfactory.cnu.ketan.spring.controller.model.repository.OrderDetailsRepository;
import com.devfactory.cnu.ketan.spring.controller.model.repository.OrderRepository;
import com.devfactory.cnu.ketan.spring.controller.model.repository.ProductRepository;
import com.devfactory.cnu.ketan.spring.controller.model.repository.UserRepository;
import com.jayway.restassured.response.Response;
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

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by ketanpatil on 18/07/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@FixMethodOrder(MethodSorters.JVM)
public class OrderControllerTest {
    private final String ordersURL = "http://localhost:8080/api/orders";
    private final String ordersIdURL = "http://localhost:8080/api/orders/{id}";
    private final String orderLineURL = "http://localhost:8080/api/orders/{id}/orderLineItem";

    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private OrderDetailsRepository orderDetailsRepo;
    @Autowired
    UserRepository userRepo;

    private Product product1,product2;
    private int pIdActive,pIdInactive;

    private Orders order;
    private int oIdActive,oIdActive1,oIdActive2,oIdInactive;

    private OrderDetails orderDetails1,orderDetails2,orderDetails3;

    @Before
    public void setUp() {
        // active product
        product1 = new Product();
        product1.setCode("code1");
        product1.setActive(1);
        product1.setQty(50);
        product1.setSell_current(70.0);
        product1.setDescription("description1");

        productRepo.save(product1);
        pIdActive = product1.getId();

        // inactive product
        product2 = new Product();
        product2.setCode("code2");
        product2.setActive(0);
        product2.setQty(50);

        productRepo.save(product2);
        pIdInactive = product2.getId();

        order = new Orders();
        order.setActive(0);
        order.setStatus("In Process");
        order = orderRepo.save(order);
        oIdActive = order.getId();

        order = new Orders();
        order.setActive(0);
        order.setStatus("In Process");
        order = orderRepo.save(order);
        oIdActive1 = order.getId();

        order = new Orders();
        order.setActive(0);
        order.setStatus("Not In process");
        order = orderRepo.save(order);
        oIdActive2 = order.getId();

        order = new Orders();
        order.setActive(0);
        order.setStatus("Not In process");
        order = orderRepo.save(order);
        oIdActive2 = order.getId();

        order = new Orders();
        order.setActive(1);
        order.setStatus("In Process");
        order = orderRepo.save(order);
        oIdInactive = order.getId();

        orderDetails1  = new OrderDetails();
        orderDetails1.setProductId(pIdActive);
        orderDetails1.setOrderId(oIdActive);
        orderDetails1.setQuantityOrdered(5);
        orderDetailsRepo.save(orderDetails1);

        orderDetails2  = new OrderDetails();
        orderDetails2.setProductId(pIdActive);
        orderDetails2.setOrderId(oIdActive1);
        orderDetails2.setQuantityOrdered(55);
        orderDetailsRepo.save(orderDetails2);

        orderDetails3  = new OrderDetails();
        orderDetails3.setProductId(pIdInactive);
        orderDetails3.setOrderId(oIdActive2);
        orderDetails3.setQuantityOrdered(5);
        orderDetailsRepo.save(orderDetails3);

    }

    @Test
    public void createOrder()
    {
        Response response = given().
                contentType("application/json").
                when().
                post(ordersURL).
                then().
                statusCode(HttpStatus.CREATED.value()).
                body("status",Matchers.is("In Process")).
                extract().response();

    }

    @Test
    public void getOrderById(){
        Response response = given().
                contentType("application/json").
                when().
                get(ordersIdURL,oIdActive).
                then().
                statusCode(200).
                body("id", Matchers.is(oIdActive)).
                extract().response();

        response = given().
                contentType("application/json").
                when().
                get(ordersIdURL,oIdInactive).
                then().
                statusCode(HttpStatus.NOT_FOUND.value()).
                extract().response();

    }

    @Test
    public void addProduct()
    {
        Map<String, Integer> req = new HashMap<>();
        req.put("product_id", pIdActive);
        req.put("qty",5);

        Response response = given().
                contentType("application/json").
                body(req).
                when().
                post(orderLineURL,oIdActive).
                then().
                statusCode(HttpStatus.CREATED.value()).
                extract().response();


        response = given().
                contentType("application/json").
                body(req).
                when().
                post(orderLineURL,oIdInactive).
                then().
                statusCode(HttpStatus.NOT_FOUND.value()).
                extract().response();


    }

    @Test
    public void addProductWithoutPid()
    {
        Map<String, Integer> req = new HashMap<>();
        req.put("product_id", null);
        req.put("qty",5);

        Response response = given().
                contentType("application/json").
                body(req).
                when().
                post(orderLineURL,oIdActive).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                extract().response();


    }

    @Test
    public void addProductWithoutQty()
    {
        Map<String, Integer> req = new HashMap<>();
        req.put("product_id", pIdActive);
        req.put("qty",null);

        Response response = given().
                contentType("application/json").
                body(req).
                when().
                post(orderLineURL,oIdActive).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                extract().response();


    }
    @Test
    public void addProductWithoutQtyKey()
    {
        Map<String, Integer> req = new HashMap<>();
        req.put("product_id", pIdActive);

        Response response = given().
                contentType("application/json").
                body(req).
                when().
                post(orderLineURL,oIdActive).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                extract().response();


    }

    @Test
    public void addProductWithoutPidKey()
    {
        Map<String, Integer> req = new HashMap<>();
        req.put("qty",5);

        Response response = given().
                contentType("application/json").
                body(req).
                when().
                post(orderLineURL,oIdActive).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                extract().response();

    }

    @Test
    public void addToClosedStatusOrder()
    {
        Map<String, Integer> req = new HashMap<>();
        req.put("product_id", pIdActive);
        req.put("qty",5);

        Response response = given().
                contentType("application/json").
                body(req).
                when().
                post(orderLineURL,oIdActive2).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                extract().response();

    }

    @Test
    public void addEmptyReq()
    {
        Map<String, Integer> req = new HashMap<>();

        Response response = given().
                contentType("application/json").
                body(req).
                when().
                post(orderLineURL,oIdActive).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                extract().response();

    }

    @Test
    public void addClosedProd()
    {
        Map<String, Integer> req = new HashMap<>();
        req.put("product_id", pIdInactive);
        req.put("qty",5);

        Response response = given().
                contentType("application/json").
                body(req).
                when().
                post(orderLineURL,oIdActive).
                then().
                statusCode(HttpStatus.NOT_FOUND.value()).
                extract().response();

    }

    @Test
    public void addToClosedOrder()
    {
        Map<String, Integer> req = new HashMap<>();
        req.put("product_id", pIdInactive);
        req.put("qty",5);

        Response response = given().
                contentType("application/json").
                body(req).
                when().
                post(orderLineURL,oIdInactive).
                then().
                statusCode(HttpStatus.NOT_FOUND.value()).
                extract().response();

    }

    @Test
    public void submitOrder()
    {
        Map<String, String> req = new HashMap<>();
        req.put("user_name", "name1");
        req.put("address","address1");
        req.put("status","checkedIn");

        Response response = given().
                contentType("application/json").
                body(req).
                when().
                patch(ordersIdURL,oIdActive).
                then().
                statusCode(HttpStatus.OK.value()).
                extract().response();

    }

    @Test
    public void submitInactiveOrder()
    {
        Map<String, String> req = new HashMap<>();
        req.put("user_name", "name1");
        req.put("address","address1");

        Response response = given().
                contentType("application/json").
                body(req).
                when().
                patch(ordersIdURL,oIdInactive).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                extract().response();

    }

    @Test
    public void submitWithOutName()
    {
        Map<String, String> req = new HashMap<>();
        req.put("user_name", "");
        req.put("address","address1");

        Response response = given().
                contentType("application/json").
                body(req).
                when().
                patch(ordersIdURL,oIdActive).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                extract().response();

    }

    @Test
    public void submitWithOutAddress()
    {
        Map<String, String> req = new HashMap<>();
        req.put("user_name", "name1");
        req.put("address","");

        Response response = given().
                contentType("application/json").
                body(req).
                when().
                patch(ordersIdURL,oIdActive).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                extract().response();

    }

    @Test
    public void submitOutOfStock()
    {
        Map<String, String> req = new HashMap<>();
        req.put("user_name", "name1");
        req.put("address","address1");

        Response response = given().
                contentType("application/json").
                body(req).
                when().
                patch(ordersIdURL,oIdActive1).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                extract().response();

    }

    @Test
    public void submitOrderInactiveProd()
    {
        Map<String, String> req = new HashMap<>();
        req.put("user_name", "name1");
        req.put("address","address1");

        Response response = given().
                contentType("application/json").
                body(req).
                when().
                patch(ordersIdURL,oIdActive2).
                then().
                statusCode(HttpStatus.BAD_REQUEST.value()).
                extract().response();

    }

    @Test
    public void deleteProduct()
    {
        Response response = given().
                contentType("application/json").
                when().
                delete(ordersIdURL, oIdActive).
                then().
                statusCode(HttpStatus.NO_CONTENT.value()).
                extract().response();

        response = given().
                contentType("application/json").
                when().
                delete(ordersIdURL, 88888888).
                then().
                statusCode(HttpStatus.NOT_FOUND.value()).
                extract().response();
    }
}
