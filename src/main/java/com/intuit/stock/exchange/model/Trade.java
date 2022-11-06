package com.intuit.stock.exchange.model;

import java.io.Serializable;

/**
 * @author monikas
 */
public class Trade implements Serializable {

    private int sellOrderId;
    private double quantity;
    private double sellPrice;
    private int buyOrderId;

    public Trade(int sellOrderId, double quantity, double sellPrice, int buyOrderId) {
        this.sellOrderId = sellOrderId;
        this.quantity = quantity;
        this.sellPrice = sellPrice;
        this.buyOrderId = buyOrderId;
    }

    public int getSellOrderId() {
        return sellOrderId;
    }

    public void setSellOrderId(int sellOrderId) {
        this.sellOrderId = sellOrderId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getBuyOrderId() {
        return buyOrderId;
    }

    public void setBuyOrderId(int buyOrderId) {
        this.buyOrderId = buyOrderId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Trade{");
        sb.append("sellOrderId=").append(sellOrderId);
        sb.append(", quantity=").append(quantity);
        sb.append(", sellPrice=").append(sellPrice);
        sb.append(", buyOrderId=").append(buyOrderId);
        sb.append('}');
        return sb.toString();
    }

}
