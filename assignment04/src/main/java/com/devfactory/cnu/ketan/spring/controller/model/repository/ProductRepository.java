package com.devfactory.cnu.ketan.spring.controller.model.repository;

import java.util.*;
import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.devfactory.cnu.ketan.spring.controller.model.Product;

/**
 * Created by ketanpatil on 07/07/16.
 */

public interface ProductRepository extends CrudRepository<Product, Integer>{
    Product findByCode(String code);
    List<Product> findByActive(int active);
    Product findByCodeAndActive(String code, int active);
    Product findByIdAndActive(int Id,int active);

}

