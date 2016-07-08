package com.devfactory.cnu.ketan.spring.controller.Controller;

import java.util.concurrent.atomic.AtomicLong;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devfactory.cnu.ketan.spring.controller.model.Product;
import com.devfactory.cnu.ketan.spring.controller.model.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ketanpatil on 07/07/16.
 */
@RestController
public class ProductController {

    final Logger logger = LoggerFactory.getLogger(ProductController.class);

    Iterable<Product> product_List = new ArrayList<Product>();

    @Autowired
    ProductRepository repo;

    // GET
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Product> getProducts()
    {
        logger.debug("Get all objects");

        List<Product> prod_List;

        prod_List = (List<Product>)(repo.findByActive(0));

        return prod_List;
    }


    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProductById(@PathVariable Integer id)
    {
        logger.debug("get entry with id {} ",id );

        Product prod ;
        prod = repo.findByIdAndActive(id,0);

        if(prod == null){
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Product>(prod,HttpStatus.OK);
    }


    @RequestMapping(value = "/products",method = RequestMethod.POST)
    public ResponseEntity<Product> addProduct(@RequestBody Product p)
    {
        logger.debug("add entry with body {} ",p );

        repo.save(p);
        return new ResponseEntity<Product>(p,HttpStatus.OK);

    }

    @RequestMapping(value = "/products/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteProduct(@PathVariable int id)
    {
        logger.debug("delete entry with id {} ",id );

        Product prod;

        prod = repo.findByIdAndActive(id,0);
        if(prod==null){
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        else{
            prod.setActive(1);
        }

        repo.save(prod);
        logger.debug("updated enitity: {}", prod);
        return new ResponseEntity<Product>(prod,HttpStatus.OK);

    }

    @RequestMapping(value = "/products/{id}",method = RequestMethod.PATCH)
    public ResponseEntity<Product> patchProduct(@PathVariable int id , @RequestBody Product p)
    {
        Product prod;

        prod = repo.findByIdAndActive(id,0);
        if(prod==null){
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        else{
            prod.setId(id);
            prod.setCode(p.getCode());
            prod.setDescription(p.getDescription());
        }

        repo.save(prod);
        logger.debug("updated enitity: {}", prod);
        return new ResponseEntity<Product>(prod,HttpStatus.OK);

    }

    @RequestMapping(value = "/products/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProduct(@PathVariable int id , @RequestBody Product p)
    {

        Product prod;

        prod = repo.findByIdAndActive(id,0);
        if(prod==null){
            prod = repo.findByCodeAndActive(p.getCode(),0);
            if(prod==null){
                return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
            }
            else{
                prod.setId(id);
                prod.setCode(p.getCode());
                prod.setDescription(p.getDescription());
            }
        }
        else{
            prod.setId(id);
            prod.setCode(p.getCode());
            prod.setDescription(p.getDescription());
        }

        repo.save(prod);
        return new ResponseEntity<Product>(prod,HttpStatus.OK);

    }


}
