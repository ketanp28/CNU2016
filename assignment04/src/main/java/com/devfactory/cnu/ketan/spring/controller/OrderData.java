package com.devfactory.cnu.ketan.spring.controller;

/**
 * Created by ketanpatil on 12/07/16.
 */
public class OrderData {
    private int prodId;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }
}
