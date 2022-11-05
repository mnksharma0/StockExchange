package com.intuit.stock.exchange.service;

import com.intuit.stock.exchange.model.Order;
import com.intuit.stock.exchange.model.Trade;

import java.util.List;

/**
 * @author monikas
 */
public interface IMatchingStrategy {

    public List<Trade> matchBuyStock(Order order);
    public List<Trade> matchSellStock(Order order);

}
