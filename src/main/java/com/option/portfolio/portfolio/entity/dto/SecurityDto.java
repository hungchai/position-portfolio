package com.option.portfolio.portfolio.entity.dto;

import java.time.Instant;

public class SecurityDto {
    private String symbol;

    private double price;
    private double position;
    private Instant priceTimestamp;

    public SecurityDto(String symbol, double price, double position, Instant priceTimestamp) {
        this.symbol = symbol;
        this.price = price;
        this.position = position;
        this.priceTimestamp = priceTimestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public Instant getPriceTimestamp() {
        return priceTimestamp;
    }

    public void setPriceTimestamp(Instant priceTimestamp) {
        this.priceTimestamp = priceTimestamp;
    }
}
