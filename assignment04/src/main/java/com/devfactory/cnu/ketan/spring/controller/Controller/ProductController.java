package com.devfactory.cnu.ketan.spring.controller.Controller;

import java.util.concurrent.atomic.AtomicLong;
import java.util.*;

import com.devfactory.cnu.ketan.spring.controller.logDecorator.ExecuteTimeInterceptor;
import com.devfactory.cnu.ketan.spring.controller.queue.SimpleQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devfactory.cnu.ketan.spring.controller.model.Product;
import com.devfactory.cnu.ketan.spring.controller.model.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ketanpatil on 07/07/16.
 */
@RestController
public class ProductController {

    final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @Autowired
    ProductRepository repo;

    // GET
    @RequestMapping(value = "/api/products", method = RequestMethod.GET)
    public List<Product> getProducts()
    {
        logger.debug("Get all objects");
        SimpleQueueService sqs  = new SimpleQueueService();
        List<Product> prod_List;
        prod_List = repo.findByActive(1);
        return prod_List;
    }


    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.GET)
    public ResponseEntity getProductById(@PathVariable Integer id)
    {
        logger.debug("get entry with id {} ",id );

        Product prod ;
        prod = repo.findByIdAndActive(id,1);
        if(prod == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(prod);
    }


    @RequestMapping(value = "/api/products",method = RequestMethod.POST)
    public ResponseEntity addProduct(@RequestBody Product p)
    {
        logger.debug("add entry with body {} ",p );
        p.setActive(1);
        repo.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(p);

    }

    @RequestMapping(value = "/api/products/{id}",method = RequestMethod.DELETE)
    public ResponseEntity deleteProduct(@PathVariable int id)
    {
        logger.debug("delete entry with id {} ",id );
        Product prod;
        prod = repo.findByIdAndActive(id,1);
        if(prod==null){
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        else{
            prod.setActive(0);
        }
        repo.save(prod);
        logger.debug("updated enitity: {}", prod);
        return ResponseEntity.status(HttpStatus.OK).body(prod);

    }

    @RequestMapping(value = "/api/products/{id}",method = RequestMethod.PATCH)
    public ResponseEntity patchProduct(@PathVariable int id , @RequestBody Product p)
    {
        Product prod;

        prod = repo.findByIdAndActive(id,1);
        if(prod==null){
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        else{
            if(p.getCode()!=null) {
                prod.setCode(p.getCode());
            }
            if(p.getDescription()!=null) {
                prod.setDescription(p.getDescription());
            }
        }

        repo.save(prod);
        logger.debug("updated enitity: {}", prod);
        return ResponseEntity.status(HttpStatus.OK).body(prod);

    }

    @RequestMapping(value = "/api/products/{id}",method = RequestMethod.PUT)
    public ResponseEntity updateProduct(@PathVariable int id , @RequestBody Product p)
    {

        Product prod;

        prod = repo.findByIdAndActive(id,1);
        if(prod==null){
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        else{
            prod.setId(id);
            prod.setCode(p.getCode());
            prod.setDescription(p.getDescription());
        }

        repo.save(prod);
        return ResponseEntity.status(HttpStatus.OK).body(prod);

    }


}
