package com.devfactory.cnu.ketan.spring.controller.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by ketanpatil on 13/07/16.
 */
@Controller
public class HealthController {
    @RequestMapping(value = "/api/health",method = RequestMethod.GET)
    public ResponseEntity<?> getHealth() {
        return ResponseEntity.ok("");
    }

}
