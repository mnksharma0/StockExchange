package com.intuit.stock.exchange.model;

import java.sql.Timestamp;

/**
 * @author monikas
 */
public class Order {

    private int orderId;
    private Timestamp time;
    private String stockCode;
    private OrderType orderType;
    private float quantity;
    private float price;

    public Order(int orderId, Timestamp time, String stockCode, OrderType orderType, float quantity, float price) {
        this.orderId = orderId;
        this.time = time;
        this.stockCode = stockCode;
        this.orderType = orderType;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStock(String stockCode) {
        this.stockCode = stockCode;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StockOrder{");
        sb.append("orderId=").append(orderId);
        sb.append(", time=").append(time);
        sb.append(", stockCode='").append(stockCode).append('\'');
        sb.append(", orderType=").append(orderType);
        sb.append(", quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
