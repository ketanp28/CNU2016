package com.devfactory.cnu.ketan.spring.controller.Controller;

import com.devfactory.cnu.ketan.spring.controller.model.Feedback;
import com.devfactory.cnu.ketan.spring.controller.model.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ketanpatil on 17/07/16.
 */
@RestController
public class FeedbackController {
    @Autowired
    FeedbackRepository feedbackRepo;

    @RequestMapping(value="/api/contactus", method= RequestMethod.POST)
    public ResponseEntity<?> addFeedback(@RequestBody String message) {
        feedbackRepo.save(new Feedback(message));
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
