package com.intuit.stock.exchange.model;

/**
 * @author monikas
 */
public class Trade {

    private int sellOrderId;
    private float quantity;
    private float sellPrice;
    private int buyOrderId;

    public Trade(int sellOrderId, float quantity, float sellPrice, int buyOrderId) {
        this.sellOrderId = sellOrderId;
        this.quantity = quantity;
        this.sellPrice = sellPrice;
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
