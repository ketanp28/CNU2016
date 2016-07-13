package com.devfactory.cnu.ketan.spring.controller.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ketanpatil on 12/07/16.
 */
@Entity
@Table(name = "orders_has_product")
public class OrderDetails {

    @Id
    @Column(name = "orders_orderId")
    private int orderId;
    @Column(name = "orders_User_userId")
    private int userId;
    @Column(name = "product_productId")
    private int productId;

    public OrderDetails(){}

    public OrderDetails(int orderId, int userId, int productId) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
