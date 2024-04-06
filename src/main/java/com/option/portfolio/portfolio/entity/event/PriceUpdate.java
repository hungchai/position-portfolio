package com.option.portfolio.portfolio.entity.event;

import java.time.Instant;

public class PriceUpdate{
    private final String symbol;

    private final double stockPrice;
    private final Instant priceTimestamp;

    public PriceUpdate(String symbol, double stockPrice, Instant priceTimestamp) {
        this.symbol = symbol;
        this.stockPrice = stockPrice;
        this.priceTimestamp = priceTimestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public Instant getPriceTimestamp() {
        return priceTimestamp;
    }
}
