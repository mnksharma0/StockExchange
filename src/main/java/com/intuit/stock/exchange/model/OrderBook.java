package com.intuit.stock.exchange.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author monikas
 */
public class OrderBook implements Serializable {

    private PriorityBlockingQueue<Order> buyOrders;
    private PriorityBlockingQueue<Order> sellOrders;
    private Timestamp lastTradedTime;
    private double lastTradedQuantity;
    private double lastTradedPrice;

    public OrderBook(){
        buyOrders = new PriorityBlockingQueue<Order>(1, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {

                //first sort based on price in descending order
                if (o1.getPrice() != o2.getPrice()) {
                    return o1.getPrice() < o2.getPrice() ? 1 : -1;
                }
                //if price is same then sort based on time
                System.out.println("sorting based on time as buy price is same");
                return o1.getTime().compareTo(o2.getTime());
            }
        });

        sellOrders = new PriorityBlockingQueue<Order>(1, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {

                //first sort based on price in ascending order
                if(o1.getPrice()!=o2.getPrice()){
                    return o1.getPrice() > o2.getPrice() ? 1 : -1;
                }
                //if price is same then sort based on time
                System.out.println("sorting based on time as sell price is same");
                return o1.getTime().compareTo(o2.getTime());
            }
        });
    }

    public PriorityBlockingQueue<Order> getBuyOrders() {
        return buyOrders;
    }

    public void setBuyOrders(PriorityBlockingQueue<Order> buyOrders) {
        this.buyOrders = buyOrders;
    }

    public PriorityBlockingQueue<Order> getSellOrders() {
        return sellOrders;
    }

    public void setSellOrders(PriorityBlockingQueue<Order> sellOrders) {
        this.sellOrders = sellOrders;
    }

    public Timestamp getLastTradedTime() {
        return lastTradedTime;
    }

    public void setLastTradedTime(Timestamp lastTradedTime) {
        this.lastTradedTime = lastTradedTime;
    }

    public double getLastTradedQuantity() {
        return lastTradedQuantity;
    }

    public void setLastTradedQuantity(double lastTradedQuantity) {
        this.lastTradedQuantity = lastTradedQuantity;
    }

    public double getLastTradedPrice() {
        return lastTradedPrice;
    }

    public void setLastTradedPrice(double lastTradedPrice) {
        this.lastTradedPrice = lastTradedPrice;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderBook{");
        sb.append("buyOrders=").append(buyOrders);
        sb.append(", sellOrders=").append(sellOrders);
        sb.append(", lastTradedTime=").append(lastTradedTime);
        sb.append(", lastTradedQuantity=").append(lastTradedQuantity);
        sb.append(", lastTradedPrice=").append(lastTradedPrice);
        sb.append('}');
        return sb.toString();
    }
}
