package com.devfactory.cnu.ketan.spring.controller.Controller;

import com.devfactory.cnu.ketan.spring.controller.model.Orders;
import com.devfactory.cnu.ketan.spring.controller.model.repository.OrderRepository;
import com.devfactory.cnu.ketan.spring.controller.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by ketanpatil on 09/07/16.
 */
public class OrderController {

    @Autowired
    OrderRepository orderRepo ;

    @Autowired
    ProductRepository productRepo;

    @RequestMapping(value = "/api/order",method = RequestMethod.GET)
    public ResponseEntity<List<Orders>> getOrders(){
        List<Orders> order_List;
        order_List = (List<Orders>)orderRepo.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(order_List);
    }

    @RequestMapping(value = "/api/order/{id}",method = RequestMethod.GET)
    public ResponseEntity getOrderById(@PathVariable Integer id){
        Orders order ;
        order = orderRepo.findOne(id);
        if(order == null){
            return new ResponseEntity<Orders>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @RequestMapping(value = "/api/Order",method = RequestMethod.POST)
    public ResponseEntity addOrder(@RequestBody Orders orderBody){
        orderRepo.save(orderBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderBody);

    }



}
