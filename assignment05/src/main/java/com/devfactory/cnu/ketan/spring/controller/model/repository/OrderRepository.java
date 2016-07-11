package com.devfactory.cnu.ketan.spring.controller.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.devfactory.cnu.ketan.spring.controller.model.Orders;

/**
 * Created by ketanpatil on 09/07/16.
 */
public interface OrderRepository extends CrudRepository<Orders,Integer> {

}
