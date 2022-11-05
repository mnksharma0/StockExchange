package com.intuit.stock.exchange.service.impl;

import com.intuit.stock.exchange.model.OrderType;
import com.intuit.stock.exchange.service.IMatchingStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author monikas
 */
public class MatchingStrategyFactory {

    private Map<OrderType, IMatchingStrategy> strategies;

    public MatchingStrategyFactory() {
        createStrategy();
    }

    public IMatchingStrategy findStrategy(OrderType strategyName) {
        return strategies.get(strategyName);
    }
    //in Spring can be injected via bean injections
    private void createStrategy() {
        strategies = new HashMap<OrderType, IMatchingStrategy>();
        for(OrderType orderType: OrderType.values()){
            switch (orderType){
                case MARKET_ORDER:
                    strategies.put(orderType, new MarketOrderMatchingStrategy());
                case LIMIT_ORDER:
                    strategies.put(orderType, new LimitOrderMatchingStrategy());
                default:
                    break;

            }
        }
    }
}
