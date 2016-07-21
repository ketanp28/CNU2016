package com.devfactory.cnu.ketan.spring.controller.model;

/**
 * Created by ketanpatil on 09/07/16.
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ORDERS")
public class Orders {

    @Column(name="ORDER_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "ORDER_DATE")
    private Date timestamp;
    @Column(name="STATUS")
    private String status;
    @Column(name="DELETED")
    private int active;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userDetails;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ORDER_DETAILS",
            joinColumns = { @JoinColumn(name = "ORDER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "PRODUCT_ID") })
    private Set<Product> products = new HashSet<Product>();

    @OneToMany(mappedBy = "orderId")
    public Set<OrderDetails> orderDetails = new HashSet<OrderDetails>();

    public Orders(){this.active=0;}

    public Orders(int id, Date timestamp, String status) {
        this.id = id;
        this.timestamp = timestamp;
        this.status = status;
    }

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
