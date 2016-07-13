package com.devfactory.cnu.ketan.spring.controller.model.repository;

import java.util.*;
import org.springframework.data.repository.CrudRepository;

import com.devfactory.cnu.ketan.spring.controller.model.User;
import org.springframework.stereotype.Component;

/**
 * Created by ketanpatil on 09/07/16.
 */
@Component
public interface UserRepository extends CrudRepository<User, Integer>{
    User findByName(String name);
}
