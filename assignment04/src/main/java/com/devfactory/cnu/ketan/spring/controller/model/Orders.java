package com.devfactory.cnu.ketan.spring.controller.model;

/**
 * Created by ketanpatil on 09/07/16.
 */

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name="orders")
public class Orders {

    @Column(name="orderId")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private java.sql.Timestamp timestamp;
    private String status;
    private Double sellprice;
    private int quantity;
    @Column(name="active")
    private int active;

    @Column(name = "User_userId")
    private int userId;

    public Orders(){}

    public Orders(int id, Timestamp timestamp, String status, Double sellprice, int quantity, int userId) {
        this.id = id;
        this.timestamp = timestamp;
        this.status = status;
        this.sellprice = sellprice;
        this.quantity = quantity;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getSellprice() {
        return sellprice;
    }

    public void setSellprice(Double sellprice) {
        this.sellprice = sellprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
