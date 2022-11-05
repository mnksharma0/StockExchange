package com.intuit.stock.exchange.controller;

import com.intuit.stock.exchange.model.Order;
import com.intuit.stock.exchange.model.Trade;
import com.intuit.stock.exchange.model.TradeType;
import com.intuit.stock.exchange.service.IMatchingStrategy;
import com.intuit.stock.exchange.service.impl.LimitOrderMatchingStrategy;
import com.intuit.stock.exchange.service.impl.MatchingStrategyFactory;
import com.intuit.stock.exchange.utils.MatchingEngineUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author monikas
 */
public class MatchingEngineController {

    private final List<Trade> result;
    private final MatchingStrategyFactory  matchingStrategyFactory;

    public MatchingEngineController() {
        result = new ArrayList<>();
        matchingStrategyFactory = new MatchingStrategyFactory(); //TODO: dependency Injectopn
    }

    public void matchStocksOrders(List<String> stockOrders){

        long startTime = System.currentTimeMillis();

            for(String order: stockOrders){

                try{
                    String[] str= order.split(" ");
                    Order stockOrder = MatchingEngineUtils.validateAndCreateOrder(str);

                    if(stockOrder==null){
                        System.out.println("Failed to process order: "+ order);
                        continue;
                    }

                    List<Trade> trades = getTrades(stockOrder);
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
        MatchingEngineUtils.printResult(result);
    }

    private List<Trade> getTrades(Order stockOrder){

        List<Trade> trades = null;
        IMatchingStrategy matchingStrategy = matchingStrategyFactory.findStrategy(stockOrder.getOrderType());

        if(stockOrder.getTradeType().equals(TradeType.buy)){
            trades= matchingStrategy.matchBuyStock(stockOrder);
        }
        else if(stockOrder.getTradeType().equals(TradeType.sell)){
            trades= matchingStrategy.matchSellStock(stockOrder);
        }
        return trades;
    }
}
