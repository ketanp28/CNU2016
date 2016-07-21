package com.devfactory.cnu.ketan.spring.controller.Controller;

import com.devfactory.cnu.ketan.spring.controller.model.Orders;
import com.devfactory.cnu.ketan.spring.controller.model.Product;
import com.devfactory.cnu.ketan.spring.controller.model.User;
import com.devfactory.cnu.ketan.spring.controller.model.OrderDetails;
import com.devfactory.cnu.ketan.spring.controller.model.repository.OrderDetailsRepository;
import com.devfactory.cnu.ketan.spring.controller.model.repository.OrderRepository;
import com.devfactory.cnu.ketan.spring.controller.model.repository.ProductRepository;
import com.devfactory.cnu.ketan.spring.controller.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Date;

/**
 * Created by ketanpatil on 09/07/16.
 */
@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepo ;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    OrderDetailsRepository orderDetailsRepo;


    @RequestMapping(value = "/api/orders/{id}",method = RequestMethod.GET)
    public ResponseEntity getOrderById(@PathVariable Integer id){
        Orders order ;
        order = orderRepo.findByIdAndActive(id,0);
        if(order == null){
            return new ResponseEntity<Orders>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @RequestMapping(value = "/api/orders",method = RequestMethod.POST)
    public ResponseEntity createOrder(){
        Orders orderBody = new Orders();

        orderBody.setStatus("In Process");
        orderBody.setActive(0);

        orderRepo.save(orderBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderBody);

    }


    // orderDetails is map of <productId,quantity>
    @RequestMapping(value = "/api/orders/{id}/orderLineItem",method = RequestMethod.POST)
    public ResponseEntity addProduct(@PathVariable Integer id,@RequestBody Map<String,Object> orderdata){
        Orders orderBody = orderRepo.findOne(id);

        if(orderBody==null||orderBody.getActive()==1)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if (orderdata.isEmpty() == true || orderdata.containsKey("qty") != true ||
                orderdata.containsKey("product_id") != true ||
                orderdata.get("qty") == null || orderdata.get("product_id") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }

        Integer pId = (Integer)(orderdata.get("product_id"));
        Product productOrdered = productRepo.findByIdAndActive(pId,1);
        int quantityOrdered = (Integer)(orderdata.get("qty"));

        try{
        if(orderBody==null || productOrdered==null) {

            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }}
        catch(Exception ex){
            ex.printStackTrace();
        }
        if (orderBody.getStatus().equals("In Process") == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Closed order ");
        }

        // check if order is already present then add quantity else create new order
        for(OrderDetails orderD : orderDetailsRepo.findAll()) {
            if (orderD.getOrderId() == id && orderD.getProductId() == pId) {
                orderD.setQuantityOrdered(orderD.getQuantityOrdered() + (Integer) orderdata.get("qty"));
                orderDetailsRepo.save(orderD);
                return ResponseEntity.status(HttpStatus.CREATED).body(orderD);
            }
        }
        OrderDetails orderDetails = new OrderDetails(
                id, pId, (Integer) orderdata.get("qty"), productOrdered.getCost(),productOrdered.getSell_current()
        );

        orderDetailsRepo.save(orderDetails);
        orderRepo.save(orderBody);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderBody);


    }

    //
    @RequestMapping(value = "/api/orders/{id}",method = RequestMethod.PATCH)
    public ResponseEntity submitOrder(@PathVariable Integer id,@RequestBody Map<String,Object> reqBody){
        System.out.print("reached");
        Orders orderBody = orderRepo.findOne(id);
        if(orderBody==null||orderBody.getActive()==1)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        if (orderBody.getStatus().equals("In Process") == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Closed order ");
        }
        if (reqBody == null || reqBody.get("address") == null || reqBody.get("user_name") == null || reqBody.get("status") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" details not specified");
        }
        System.out.print("reached2");
        String username = (String) reqBody.get("user_name");
        String address = (String) reqBody.get("address");
        User userBody = userRepo.findByCode(username);
        System.out.print("reached3");
        // check if order is already present then add quantity else create new order
        for(OrderDetails orderDetail : orderBody.orderDetails) {
            Product product = productRepo.findOne(orderDetail.getProductId());
            int remStock = product.getQty() - orderDetail.getQuantityOrdered();
            if(remStock < 0){
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("out of stock");
            }
        }
        for(OrderDetails orderDetails : orderBody.orderDetails) {
            Product product = productRepo.findOne(orderDetails.getProductId());
            int remStock = product.getQty() - orderDetails.getQuantityOrdered();
            product.setQty(remStock);
            productRepo.save(product);
            orderDetailsRepo.save(orderDetails);
        }
        System.out.print("reached4");
        orderBody.setTimestamp(new Date());
        orderBody.setStatus((String)reqBody.get("status"));
        if (userBody == null) {
            User newUser = new User(username,address);
            userRepo.save(newUser);
            orderBody.setUserDetails(newUser);
        }
        else{
            userBody.setAddress(address);
            userRepo.save(userBody);
            orderBody.setUserDetails(userBody);
        }
        System.out.print("reached5");
        orderRepo.save(orderBody);
        return ResponseEntity.status(HttpStatus.OK).body(orderBody);

    }

    @RequestMapping(value = "/api/orders/{id}",method = RequestMethod.DELETE)
    public ResponseEntity deleteOrder(@PathVariable Integer id){
        Orders orderBody = orderRepo.findOne(id);

        if(orderBody!=null){
            orderBody.setActive(1);
            orderRepo.save(orderBody);

            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }



}
