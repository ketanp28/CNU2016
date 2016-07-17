package com.devfactory.cnu.ketan.spring.controller.model;

import javax.persistence.*;

/**
 * Created by ketanpatil on 17/07/16.
 */
@Entity
@Table(name="FEEDBACK")
public class Feedback {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    private String message;

    public Feedback(String message) {
        this.message = message;
    }

    public Feedback() {}
}
