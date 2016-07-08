package com.devfactory.cnu.ketan.spring.controller.model;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;


/**
 * Created by ketanpatil on 07/07/16.
 */
@Entity
@Table(name="product")
public class Product {
    @Column(name="productid")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name="productcode")
    private String code;

    @Column(name="productdesc")
    private String description;

    @Column(name="active", columnDefinition = "tinyint")
    private int active;

    protected Product(){}

    public Product(int id,String code,String description){
        this.id =id;
        this.code = code;
        this.description=description;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setActive(int active){
        this.active = active;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public int getActive() {
        return active;
    }
    public String getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }

}
