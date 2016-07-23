package com.devfactory.cnu.ketan.spring.controller.model;

import javax.persistence.*;

/**
 * Created by ketanpatil on 12/07/16.
 */
@Entity
@Table(name = "ORDER_DETAILS")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ORDER_DETAILS_ID")
    private int orderDetailId;
    @Column(name="ORDER_ID")
    private int orderId;

    @Column(name="PRODUCT_ID")
    private Integer productId;
    @Column(name="QUANTITY_ORDERED")
    public Integer quantityOrdered;
    @Column(name="COST_PRICE")
    public Double costPrice;
    @Column(name="SELLING_PRICE")
    public Double sellingPrice;

    public OrderDetails(){}

    public OrderDetails(int orderId, int productId, int quantityOrdered, Double costPrice, Double sellingPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantityOrdered = quantityOrdered;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
