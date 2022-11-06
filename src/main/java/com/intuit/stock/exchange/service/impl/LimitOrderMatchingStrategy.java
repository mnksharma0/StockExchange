package com.intuit.stock.exchange.service.impl;

import com.intuit.stock.exchange.model.Order;
import com.intuit.stock.exchange.model.OrderBook;
import com.intuit.stock.exchange.model.Trade;
import com.intuit.stock.exchange.model.TradeType;
import com.intuit.stock.exchange.service.IMatchingStrategy;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author monikas
 */
public class LimitOrderMatchingStrategy implements IMatchingStrategy {

    private final Hashtable<String, OrderBook> stockOrderBooks;

    public LimitOrderMatchingStrategy() {
        stockOrderBooks= new Hashtable<>();
    }

    private Trade createTradeAndUpdateOrderBook(Order buyOrder, Order sellOrder){

        double quantity= buyOrder.getQuantity();

        if(buyOrder.getQuantity()==sellOrder.getQuantity()){
            buyOrder.setQuantity(0);
            sellOrder.setQuantity(0);
        } else if(buyOrder.getQuantity()>sellOrder.getQuantity()){
            buyOrder.setQuantity(buyOrder.getQuantity() - sellOrder.getQuantity());
            sellOrder.setQuantity(0);
        }
        else{
            sellOrder.setQuantity(sellOrder.getQuantity() - buyOrder.getQuantity());
            buyOrder.setQuantity(0);
        }

        quantity= buyOrder.getQuantity()==0? quantity: quantity-buyOrder.getQuantity();

        removeOrderFromStockBook(buyOrder, sellOrder);

        Trade trade= new Trade(sellOrder.getOrderId(), quantity, sellOrder.getPrice(), buyOrder.getOrderId());
        System.out.println("Trade: "+ sellOrder.getOrderId() +" "+quantity+" "+sellOrder.getPrice()+ " "+buyOrder.getOrderId());

        updateLastTradeInfo(buyOrder, sellOrder, quantity);

        return trade;
    }

    private void removeOrderFromStockBook(Order buyOrder, Order sellOrder){

        if(stockOrderBooks.get(buyOrder.getStockCode())!=null){
            if(sellOrder.getQuantity()==0){
                getStockSellOrders(buyOrder.getStockCode()).remove(sellOrder);
            }
            if(buyOrder.getQuantity()==0){
                getStockBuyOrders(buyOrder.getStockCode()).remove(buyOrder);
            }
        }
    }

    private void updateLastTradeInfo(Order buyOrder, Order sellOrder, double quantity){

        stockOrderBooks.get(buyOrder.getStockCode()).setLastTradedPrice(sellOrder.getPrice());
        stockOrderBooks.get(buyOrder.getStockCode()).setLastTradedQuantity(quantity);
        stockOrderBooks.get(buyOrder.getStockCode()).setLastTradedTime(new Timestamp(System.currentTimeMillis()));

    }

    private boolean isStockHasSellOrdersPresent(String stockCode){

        boolean sellOrdersPresent = false;

        if(stockOrderBooks.containsKey(stockCode) && !getStockSellOrders(stockCode).isEmpty()){
            sellOrdersPresent = true;
        }
        return sellOrdersPresent;
    }

    private boolean isStockHasBuyOrdersPresent(String stockCode){

        boolean buyOrdersPresent = false;

        if(stockOrderBooks.containsKey(stockCode) && !getStockBuyOrders(stockCode).isEmpty()){
            buyOrdersPresent = true;
        }
        return buyOrdersPresent;
    }

    private PriorityBlockingQueue<Order> getStockSellOrders(String stockCode){
        return stockOrderBooks.get(stockCode).getSellOrders();
    }

    private PriorityBlockingQueue<Order> getStockBuyOrders(String stockCode){
        return stockOrderBooks.get(stockCode).getBuyOrders();
    }

    private boolean isOrderExpired(Order order){
        return order.getExpiryTime().before(new Timestamp(System.currentTimeMillis()));
    }

    private void handleExpiredOrder(Order order){
        if(order.getTradeType().equals(TradeType.buy)){
            getStockBuyOrders(order.getStockCode()).remove(order);
        }
        else if(order.getTradeType().equals(TradeType.sell)){
            getStockSellOrders(order.getStockCode()).remove(order);
        }
    }

    @Override
    public List<Trade> matchBuyStock(Order buyOrder) {
        System.out.println("Processing buy buyOrder: "+buyOrder);

        String stockCode = buyOrder.getStockCode();
        List<Trade> trades=new ArrayList<>();

        if(isStockHasSellOrdersPresent(stockCode)){
            Order sellOrder = getStockSellOrders(stockCode).peek();
             while(isStockHasSellOrdersPresent(stockCode) && sellOrder.getPrice()<= buyOrder.getPrice() && buyOrder.getQuantity() >0){
               if(!isOrderExpired(sellOrder)){
                   Trade trade = createTradeAndUpdateOrderBook(buyOrder,sellOrder);
                   trades.add(trade);
               }
               else{
                   handleExpiredOrder(sellOrder);
               }
                 sellOrder = getStockSellOrders(stockCode).peek();
            }
        }

        if(buyOrder.getQuantity()!=0) {
            if (!stockOrderBooks.containsKey(buyOrder.getStockCode())) {
                OrderBook orderBook = new OrderBook();
                orderBook.getBuyOrders().add(buyOrder);
                stockOrderBooks.put(buyOrder.getStockCode(), orderBook);
            } else {
                stockOrderBooks.get(buyOrder.getStockCode()).getBuyOrders().add(buyOrder);
            }
        }
        return trades;
    }

    @Override
    public List<Trade> matchSellStock(Order sellOrder) {
        System.out.println("Processing sellOrder: "+sellOrder);
        List<Trade> trades= new ArrayList<>();

        String stockCode = sellOrder.getStockCode();

        if(isStockHasBuyOrdersPresent(stockCode)){
            Order buyOrder = getStockBuyOrders(stockCode).peek();
            while(isStockHasBuyOrdersPresent(stockCode) && buyOrder.getPrice() >= sellOrder.getPrice() && sellOrder.getQuantity() > 0) {
                if(!isOrderExpired(buyOrder)){
                    Trade trade = createTradeAndUpdateOrderBook(buyOrder,sellOrder);
                    trades.add(trade);
                }
                else{
                    handleExpiredOrder(buyOrder);
                }
                buyOrder = getStockBuyOrders(stockCode).peek();
            }
        }
        if(sellOrder.getQuantity()!=0){
            if (!stockOrderBooks.containsKey(sellOrder.getStockCode())) {
                OrderBook orderBook = new OrderBook();
                orderBook.getSellOrders().add(sellOrder);
                stockOrderBooks.put(sellOrder.getStockCode(), orderBook);
            } else {
                stockOrderBooks.get(sellOrder.getStockCode()).getSellOrders().add(sellOrder);
            }
        }
        return trades;
    }
}
