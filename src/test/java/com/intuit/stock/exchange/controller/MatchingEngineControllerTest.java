package com.intuit.stock.exchange.controller;

import com.intuit.stock.exchange.model.Trade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author monikas
 */
class MatchingEngineControllerTest {

    private MatchingEngineController controller;

    public MatchingEngineControllerTest() {
        controller= new MatchingEngineController();
    }

    @Test
    void TestGivenInQuestion() throws Exception {

        List<String> ordersList= new ArrayList();
        ordersList.add("1 09:45 BAC sell 100 240.10");
        ordersList.add("2 09:45 BAC sell 90 237.45");
        ordersList.add("3 09:47 BAC buy 80 238.10");
        ordersList.add("5 09:48 BAC sell 220 241.50");
        ordersList.add("6 09:49 BAC buy 50 238.50");
        ordersList.add("7 09:52 TCS buy 10 1001.10");
        ordersList.add("8 10:01 BAC sell 20 240.10");
        ordersList.add("9 10:02 BAC buy 150 242.70");

        List<Trade> result = controller.matchStocksOrders(ordersList);

        assertTrue(result != null);
        assertTrue(result.size() == 5);
        assertEquals(result.get(0).toString(), new Trade(2, 80, 237.45, 3).toString());
        assertEquals(result.get(1).toString(), new Trade(2, 10, 237.45, 6).toString());
        assertEquals(result.get(2).toString(), new Trade(1, 100, 240.1, 9).toString());
        assertEquals(result.get(3).toString(), new Trade(8, 20, 240.1, 9).toString());
        assertEquals(result.get(4).toString(), new Trade(5, 30, 241.5, 9).toString());
    }

    @Test
    void TestNoTradeOnlySell() throws Exception {

        List<String> ordersList= new ArrayList();
        ordersList.add("1 09:45 BAC sell 100 240.10");
        ordersList.add("2 09:45 BAC sell 90 237.45");
        ordersList.add("5 09:48 BAC sell 220 241.50");
        ordersList.add("8 10:01 BAC sell 20 240.10");

        List<Trade> result = controller.matchStocksOrders(ordersList);

        assertTrue(result != null);
        assertTrue(result.size() == 0);
    }

    @Test
    void TestNoTradeOnlyBuy() throws Exception {

        List<String> ordersList= new ArrayList();
        ordersList.add("3 09:47 BAC buy 80 238.10");
        ordersList.add("6 09:49 BAC buy 50 238.50");
        ordersList.add("7 09:52 TCS buy 10 1001.10");
        ordersList.add("9 10:02 BAC buy 150 242.70");

        List<Trade> result = controller.matchStocksOrders(ordersList);

        assertTrue(result != null);
        assertTrue(result.size() == 0);
    }

    @Test
    void TestSellGreaterThenBuy() throws Exception {

        List<String> ordersList= new ArrayList();
        ordersList.add("1 09:45 BAC sell 100 240.10");
        ordersList.add("2 09:45 BAC sell 90 241.45");
        ordersList.add("3 09:47 BAC buy 80 238.10");
        ordersList.add("6 09:49 BAC buy 50 237.50");

        List<Trade> result = controller.matchStocksOrders(ordersList);

        assertTrue(result != null);
        assertTrue(result.size() == 0);
    }

    @Test
    void TestLowerSellPriceFulfilledFirst() throws Exception {

        List<String> ordersList= new ArrayList();
        ordersList.add("1 09:45 BAC sell 100 240.10");
        ordersList.add("2 09:46 BAC sell 90 237.45"); // This order came later but sell price is low
        ordersList.add("3 09:47 BAC buy 80 238.10");

        List<Trade> result = controller.matchStocksOrders(ordersList);

        assertTrue(result != null);
        assertTrue(result.size() == 1);
        assertEquals(result.get(0).toString(), new Trade(2, 80, 237.45, 3).toString());
    }

    @Test
    void TestTimestampWhenSellPriceIsSame() throws Exception {

        List<String> ordersList= new ArrayList();
        ordersList.add("1 09:45 BAC sell 100 237.45");
        ordersList.add("2 09:46 BAC sell 90 237.45"); // same sell price as order 1
        ordersList.add("3 09:47 BAC buy 80 238.10");

        List<Trade> result = controller.matchStocksOrders(ordersList);

        assertTrue(result != null);
        assertTrue(result.size() == 1);
        assertEquals(result.get(0).toString(), new Trade(1, 80, 237.45, 3).toString());
    }

    @Test
    void TestValidationFailedTime() {

        List<String> ordersList= new ArrayList();
        ordersList.add("1 time BAC sell 100 237.45");

        Exception exception = assertThrows(ParseException.class, () -> {
                    List<Trade> result = controller.matchStocksOrders(ordersList);
                });

        assertTrue(exception != null);
    }

    @Test
    void TestValidationFailedPrice() {

        List<String> ordersList= new ArrayList();
        ordersList.add("1 9:47 BAC sell 100 =price");

        Exception exception = assertThrows(NumberFormatException.class, () -> {
            List<Trade> result = controller.matchStocksOrders(ordersList);
        });

        assertTrue(exception != null);
    }

    @Test
    void TestExpiredOrders() throws Exception {

        List<String> ordersList= new ArrayList();
        ordersList.add("1 09:45 BAC sell 100 236.45 2022-11-01-12:50");
        ordersList.add("2 09:46 BAC sell 90 237.45");
        ordersList.add("3 09:47 BAC buy 80 238.10");

        List<Trade> result = controller.matchStocksOrders(ordersList);

        assertTrue(result != null);
        assertTrue(result.size() == 1);
        assertEquals(result.get(0).toString(), new Trade(2, 80, 237.45, 3).toString());
    }
}