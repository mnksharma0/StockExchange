package com.intuit.stock.exchange;

import com.intuit.stock.exchange.controller.MatchingEngineController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author monikas
 */
public class MatchingEngineMain {

    public static void main(String[] args){

        String inputString1 = "1 09:45 BAC sell 100 240.10";
        String inputString2 = "2 09:45 BAC sell 90 237.45";
        String inputString3 = "3 09:47 BAC buy 80 238.10";
        String inputString4 = "5 09:48 BAC sell 220 241.50";
        String inputString5 = "6 09:49 BAC buy 50 238.50";
        String inputString6 = "7 09:52 TCS buy 10 1001.10";
        String inputString7 = "8 10:01 BAC sell 20 240.10";
        String inputString8 = "9 10:02 BAC buy 150 242.70";

        List<String> orderList = new ArrayList<String>();
        orderList.add(inputString1);
        orderList.add(inputString2);
        orderList.add(inputString3);
        orderList.add(inputString4);
        orderList.add(inputString5);
        orderList.add(inputString6);
        orderList.add(inputString7);
        orderList.add(inputString8);

        MatchingEngineController controller = new MatchingEngineController();
        controller.matchStocksOrders(orderList);

    }

}
