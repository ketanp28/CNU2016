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
@Table(name="PRODUCT")
public class Product {
    @Column(name="PRODUCT_ID")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column(name="PRODUCT_CODE")
    private String code;
    @Column(name="PRODUCT_DESCRIPTION")
    private String description;
    @Column(name="IS_AVAILABLE")
    private int active;
    @Column(name="QUANTITY_IN_STOCK")
    private Integer qty;
    @Column(name="COST_PRICE")
    private Double cost;
    @Column(name="SELLING_PRICE")
    private Double sell_current;


    public Product(){
        qty = 0;
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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer inventry) {
        this.qty = inventry;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getSell_current() {
        return sell_current;
    }

    public void setSell_current(Double sellCurrent) {
        this.sell_current = sellCurrent;
    }
}
