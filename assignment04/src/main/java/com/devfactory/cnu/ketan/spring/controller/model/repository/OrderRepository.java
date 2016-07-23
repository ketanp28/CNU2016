package com.devfactory.cnu.ketan.spring.controller.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.devfactory.cnu.ketan.spring.controller.model.Orders;
import org.springframework.stereotype.Component;

/**
 * Created by ketanpatil on 09/07/16.
 */
@Component
public interface OrderRepository extends CrudRepository<Orders,Integer> {
    Orders findByIdAndActive(Integer Id,int active);

}
