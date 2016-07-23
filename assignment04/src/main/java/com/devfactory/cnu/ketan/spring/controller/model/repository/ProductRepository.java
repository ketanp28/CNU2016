package com.devfactory.cnu.ketan.spring.controller.model.repository;

import java.util.*;
import org.springframework.data.repository.CrudRepository;

import com.devfactory.cnu.ketan.spring.controller.model.Product;
import org.springframework.stereotype.Component;

/**
 * Created by ketanpatil on 07/07/16.
 */
@Component
public interface ProductRepository extends CrudRepository<Product, Integer>{
    List<Product> findByActive(int active);
    Product findByIdAndActive(int Id,int active);

}

