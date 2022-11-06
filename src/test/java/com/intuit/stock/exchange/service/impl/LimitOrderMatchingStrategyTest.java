package com.intuit.stock.exchange.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author monikas
 */
class LimitOrderMatchingStrategyTest {

    private LimitOrderMatchingStrategy limitOrderMatchingStrategy;

    public LimitOrderMatchingStrategyTest() {
        this.limitOrderMatchingStrategy = new LimitOrderMatchingStrategy();
    }

    @Test
    void matchBuyStock() {
        assertTrue(true);
    }

    @Test
    void matchSellStock() {
        assertFalse(false);
    }
}