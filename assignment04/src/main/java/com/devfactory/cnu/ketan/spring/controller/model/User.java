package com.devfactory.cnu.ketan.spring.controller.model;

import javax.persistence.*;

/**
 * Created by ketanpatil on 09/07/16.
 */
@Entity
@Table(name = "USER_DETAILS")
public class User {

    @Column(name = "USER_ID")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name="COMPANY_NAME")
    private String code;

    @Column(name="CONTACT_FIRST_NAME")
    private String name;

    @Column(name="PHONE")
    private String phone;

    @Column(name="ADDRESS_LINE")
    private String address;

    public User(String code, String address) {
        this.code = code;
        this.address = address;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
