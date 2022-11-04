package com.intuit.stock.exchange.service.impl;

import com.intuit.stock.exchange.model.Order;
import com.intuit.stock.exchange.model.OrderBook;
import com.intuit.stock.exchange.model.Trade;
import com.intuit.stock.exchange.service.MatchingEngineService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @author monikas
 */
public class MatchingEngineServiceImpl implements MatchingEngineService {

    private static Hashtable<String, OrderBook> stockOrderBooks= new Hashtable<>();

    private Trade createTradeAndUpdateOrderBook(Order buyOrder, Order sellOrder){

        float quantity= buyOrder.getQuantity();
        if(buyOrder.getQuantity()==sellOrder.getQuantity()){
            buyOrder.setQuantity(0);
            sellOrder.setQuantity(0);
        }
        else if(buyOrder.getQuantity()>sellOrder.getQuantity()){
            buyOrder.setQuantity(buyOrder.getQuantity() - sellOrder.getQuantity());
            sellOrder.setQuantity(0);
        }
        else{
            sellOrder.setQuantity(sellOrder.getQuantity() - buyOrder.getQuantity());
            buyOrder.setQuantity(0);
        }

        quantity= buyOrder.getQuantity()==0? quantity: quantity-buyOrder.getQuantity();

        if(stockOrderBooks.get(buyOrder.getStockCode())!=null){
            if(sellOrder.getQuantity()==0){
                stockOrderBooks.get(buyOrder.getStockCode()).getSellOrders().remove(sellOrder);
            }
            if(buyOrder.getQuantity()==0){
                stockOrderBooks.get(buyOrder.getStockCode()).getBuyOrders().remove(buyOrder);
            }
        }

        Trade trade= new Trade(sellOrder.getOrderId(), quantity, sellOrder.getPrice(), buyOrder.getOrderId());
        System.out.println("Trade: "+ sellOrder.getOrderId() +" "+quantity+" "+sellOrder.getPrice()+ " "+buyOrder.getOrderId());

        //update last trade info
        stockOrderBooks.get(buyOrder.getStockCode()).setLastTradedPrice(sellOrder.getPrice());
        stockOrderBooks.get(buyOrder.getStockCode()).setLastTradedQuantity(quantity);
        stockOrderBooks.get(buyOrder.getStockCode()).setLastTradedTime(new Timestamp(System.currentTimeMillis()));

        return trade;
    }

    @Override
    public List<Trade> matchBuyStock(Order buyOrder) {
        System.out.println("Processing buy buyOrder: "+buyOrder);

        List<Trade> trades=new ArrayList<>();
        //stock has sell orders present
        if(stockOrderBooks.containsKey(buyOrder.getStockCode()) && !stockOrderBooks.get(buyOrder.getStockCode()).getSellOrders().isEmpty()){
            while(stockOrderBooks.get(buyOrder.getStockCode()).getSellOrders().peek().getPrice()<= buyOrder.getPrice() && buyOrder.getQuantity() >0){
                Order sellOrder = stockOrderBooks.get(buyOrder.getStockCode()).getSellOrders().peek();
                Trade trade = createTradeAndUpdateOrderBook(buyOrder,sellOrder);
                trades.add(trade);
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
        System.out.println("Processing sell sellOrder: "+sellOrder);
        List<Trade> trades= new ArrayList<>();

        //stock has buy orders present
        if(stockOrderBooks.containsKey(sellOrder.getStockCode()) && !stockOrderBooks.get(sellOrder.getStockCode()).getBuyOrders().isEmpty()){
            while(stockOrderBooks.get(sellOrder.getStockCode()).getBuyOrders().peek().getPrice() >= sellOrder.getPrice() && sellOrder.getQuantity() > 0) {
                Order buyOrder = stockOrderBooks.get(sellOrder.getStockCode()).getBuyOrders().peek();
                Trade trade= createTradeAndUpdateOrderBook(buyOrder,sellOrder);
                trades.add(trade);
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
