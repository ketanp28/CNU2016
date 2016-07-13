package com.devfactory.cnu.ketan.spring.controller.model.repository;

import com.devfactory.cnu.ketan.spring.controller.model.OrderDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Created by ketanpatil on 12/07/16.
 */
@Component
public interface OrderDetailsRepository extends CrudRepository<OrderDetails,Integer>{
    //List<OrderDetails> findByOrderIdAndUserId(int orderId,int userId);
    OrderDetails findByOrderIdAndUserId(int orderId,int userId);
}
