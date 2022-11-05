package com.intuit.stock.exchange.service.impl;

import com.intuit.stock.exchange.model.Order;
import com.intuit.stock.exchange.model.Trade;
import com.intuit.stock.exchange.service.IMatchingStrategy;

import java.util.List;

/**
 * @author monikas
 */
public class MarketOrderMatchingStrategy implements IMatchingStrategy {
    @Override
    public List<Trade> matchBuyStock(Order order) {
        return null;
    }

    @Override
    public List<Trade> matchSellStock(Order order) {
        return null;
    }
}
