package com.intuit.stock.exchange.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author monikas
 */
public class Order implements Serializable {

    private int orderId;
    private Timestamp time;
    private String stockCode;
    private OrderType orderType;
    private TradeType tradeType;
    private double quantity;
    private double price;
    private OrderStatus status;
    private Timestamp expiryTime;

    public Order(int orderId, Timestamp time, String stockCode, OrderType orderType, TradeType tradeType, double quantity, double price, OrderStatus status, Timestamp expiryTime) {
        this.orderId = orderId;
        this.time = time;
        this.stockCode = stockCode;
        this.orderType = orderType;
        this.tradeType = tradeType;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.expiryTime = expiryTime;
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

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Timestamp getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Timestamp expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", time=").append(time);
        sb.append(", stockCode='").append(stockCode).append('\'');
        sb.append(", orderType=").append(orderType);
        sb.append(", tradeType=").append(tradeType);
        sb.append(", quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append(", status=").append(status);
        sb.append(", expiryTime=").append(expiryTime);
        sb.append('}');
        return sb.toString();
    }
}
