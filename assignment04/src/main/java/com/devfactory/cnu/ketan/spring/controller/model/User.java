package com.devfactory.cnu.ketan.spring.controller.model;

import javax.persistence.*;

/**
 * Created by ketanpatil on 09/07/16.
 */
@Entity
@Table(name = "User")
public class User {

    @Column(name = "userId")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name="userCode")
    private String code;

    private String name;

    private String phone;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
