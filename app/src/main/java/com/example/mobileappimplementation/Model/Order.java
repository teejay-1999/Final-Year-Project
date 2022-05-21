package com.example.mobileappimplementation.Model;

import java.sql.Timestamp;
import java.sql.Date;

public class Order {
    private Date date;
    private String status;
    private Timestamp createdAt;
    private OrderDetail orderDetail;
    private Customer customer;

    public Order(Date date, OrderDetail orderDetail, Customer customer) {
        this.date = date;
        this.status = status;
        this.orderDetail = orderDetail;
        this.customer = customer;
        Long currentDate = System.currentTimeMillis();
        createdAt =  new Timestamp(currentDate);
        System.out.println(createdAt);
        status = "pending";
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
