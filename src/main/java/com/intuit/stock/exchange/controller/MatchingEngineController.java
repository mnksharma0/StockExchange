package com.intuit.stock.exchange.controller;

import com.intuit.stock.exchange.model.OrderType;
import com.intuit.stock.exchange.model.Order;
import com.intuit.stock.exchange.model.Trade;
import com.intuit.stock.exchange.service.impl.MatchingEngineServiceImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author monikas
 */
public class MatchingEngineController {

    private static final String FORMAT="HH:mm";
    private static final List<Trade> result = new ArrayList<>();

    public void matchStocksOrders(List<String> stockOrders){

        MatchingEngineServiceImpl stockExchangeService = new MatchingEngineServiceImpl();

        long startTime = System.currentTimeMillis();

            for(String order: stockOrders){

                try{
                    String[] str= order.split(" ");
                    SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
                    Date parsedDate = dateFormat.parse(str[1]);
                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    OrderType orderType = OrderType.valueOf(str[3]);
                    //Can call a validation helper class to validate all the inputs
                    Order stockOrder = new Order(Integer.parseInt(str[0]), timestamp, str[2], orderType, Float.parseFloat(str[4]), Float.parseFloat(str[5]));
                    List<Trade> trades = null;
                    if(orderType.equals(OrderType.buy)){
                        trades= stockExchangeService.matchBuyStock(stockOrder);
                    }
                    else if(orderType.equals(OrderType.sell)){
                        trades= stockExchangeService.matchSellStock(stockOrder);
                    }
                    System.out.println("trades:"+ trades);
                    if(trades!=null && !trades.isEmpty())
                        result.addAll(trades);
                }
                catch (Exception e){
                    System.out.println("Exception occurred for order: "+ order +" Exception: "+e);
                    e.printStackTrace();
                }
            }
        long endTime = System.currentTimeMillis();
        System.out.println("Total time taken to process all orders= "+ String.valueOf(endTime-startTime));
        printResult();
    }

    private static void printResult(){
        System.out.println("Final Output:");
        for(Trade trade: result){
            System.out.println(trade.toString());
        }
    }
}
