# StockExchange

- Design a Matching Engine for a Stock Exchange.
- Buy and Sell orders with quantity and price.
- Orders can be matched only till the end of the trading day.
- Follows FIFO Price-Time order matching rule.
- Lower selling prices and higher buying prices gets the priority.
- Trade happens when buy price>= selling price.
- Trade is recorded at the selling price.


Assumptions:

- Solution is for a single stock exchange in a country.
- Only for Limit Order (can be easily extensible for market and stop order types).
- Partial Orders can be fulfilled.
- Order time in the input is of the same trading day.
- Trading day ends at 3.30 p.m.
