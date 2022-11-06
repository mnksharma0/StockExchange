package com.intuit.stock.exchange.utils;

import com.intuit.stock.exchange.model.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author monikas
 */
public class MatchingEngineUtils {

    public static Order validateAndCreateOrder(String[] str) throws Exception {

        if(str.length < 6){
            System.out.println("Input not in correct format. str: " + str.toString());
            return null;
        }

        Order stockOrder =null;
        try{
            int orderId= Integer.parseInt(str[0]);

            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.TIMEFORMAT);
            Date parsedDate = dateFormat.parse(str[1]);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

            String stockCode = str[2];
            OrderType orderType = OrderType.LIMIT_ORDER;
            TradeType tradeType = TradeType.valueOf(str[3]);
            Double quantity = Double.parseDouble(str[4]);
            Double price = Double.valueOf(str[5]);
            OrderStatus status = OrderStatus.IN_PROCESS;

            SimpleDateFormat expiryDateFormat = new SimpleDateFormat(Constants.DATEPATTERN);
            Timestamp expiryTime = new Timestamp(System.currentTimeMillis());
            if( str.length == 7 && str[6] != null)
            {
                parsedDate = expiryDateFormat.parse(str[6]);
                expiryTime = new java.sql.Timestamp(parsedDate.getTime());
            }
            else {
                expiryTime.setHours(23);
                expiryTime.setMinutes(59);
                expiryTime.setSeconds(59);
            }

            stockOrder  = new Order(orderId, timestamp, stockCode, orderType, tradeType, quantity, price, status, expiryTime);

        } catch (ParseException e) {
            System.out.println("Time is in incorrect format. value= "+str[1]);
            throw e;
        } catch (Exception e) {
            System.out.println("Input Validation failed with exception: "+e);
            throw e;
        }

        return stockOrder;
    }

    public static void printResult(List<Trade> result){
        System.out.println("Final Output:");
        for(Trade trade: result){
            System.out.println(trade.toString());
        }
    }
}
