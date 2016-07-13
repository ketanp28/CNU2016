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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.sql.Timestamp;

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

    @RequestMapping(value = "/api/order",method = RequestMethod.GET)
    public ResponseEntity<List<Orders>> getOrders(){
        List<Orders> order_List;
        order_List = (List<Orders>)orderRepo.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(order_List);
    }

    @RequestMapping(value = "/api/orders/{id}",method = RequestMethod.GET)
    public ResponseEntity getOrderById(@PathVariable Integer id){
        Orders order ;
        order = orderRepo.findOne(id);
        if(order == null){
            return new ResponseEntity<Orders>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @RequestMapping(value = "/api/orders",method = RequestMethod.POST)
    public ResponseEntity createOrder(@RequestBody Map<String,Object> body){
        Orders orderBody = new Orders();
        int id;

        String userName = body.get("user_name").toString();
        if(StringUtils.isEmpty(userName)){
            // return bad request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body empty!");
        }
        User user = userRepo.findByName(userName);
        if(user==null){
            User newUser = new User();
            newUser.setCode(userName);
            userRepo.save(newUser);
            id = newUser.getId();
        }
        else{
            id = user.getId();
        }
        orderBody.setUserId(id);
        java.util.Date date= new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        orderBody.setTimestamp(timestamp);
        orderBody.setStatus("In Process");

        orderRepo.save(orderBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderBody);

    }

    // orderDetails is map of <productId,quantity>
    @RequestMapping(value = "/api/orders/{id}/orderLineItem",method = RequestMethod.POST)
    public ResponseEntity addProduct(@PathVariable Integer id,@RequestBody Map<String,Object> orderdata){
        Orders orderBody = orderRepo.findOne(id);
        Product productOrdered = productRepo.findByIdAndActive((Integer)(orderdata.get("product_id")),1);
        int quantityOrdered = (Integer)(orderdata.get("qty"));

        if(orderBody==null || productOrdered==null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);


        orderBody.setQuantity(quantityOrdered);
        orderBody.setSellprice(productOrdered.getSellCurrent());
        OrderDetails orderDetails = new OrderDetails(id,orderBody.getUserId(),productOrdered.getId());

        orderDetailsRepo.save(orderDetails);
        orderRepo.save(orderBody);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderBody);


    }

    //
    @RequestMapping(value = "/api/orders/{id}",method = RequestMethod.PATCH)
    public ResponseEntity submitOrder(@PathVariable Integer id,@RequestBody Map<String,Object> body){
        Orders orderBody = orderRepo.findOne(id);
        if(orderBody==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if(StringUtils.isEmpty(body.get("address").toString()))
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        OrderDetails orderDetails = orderDetailsRepo.findByOrderIdAndUserId(orderBody.getId(),orderBody.getUserId());
        Product productOrdered = productRepo.findByIdAndActive(orderDetails.getProductId(),1);
        User userBody = userRepo.findOne(orderBody.getUserId());

        int quantityOrdered = orderBody.getQuantity();

        if(quantityOrdered <= productOrdered.getQty()){
            productOrdered.setQty(productOrdered.getQty()-quantityOrdered);
            orderBody.setStatus("checkout");

            productRepo.save(productOrdered);
            orderRepo.save(orderBody);

            return ResponseEntity.status(HttpStatus.OK).body(orderBody);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @RequestMapping(value = "/api/orders/{id}",method = RequestMethod.DELETE)
    public ResponseEntity deleteOrder(@PathVariable Integer id){
        Orders orderBody = orderRepo.findOne(id);

        if(orderBody!=null){
            orderBody.setActive(0);
            orderRepo.save(orderBody);

            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
