package com.option.portfolio.portfolio.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@Slf4j
@Service
public class MockMarketDataProvider {
    private Map<String, Double> stockPrices = new ConcurrentHashMap<>();
    private Random random = new Random();
    @Value("${mockMarketPriceProvider.gbm.dt:2}")
    private double dt; // Time step

    @Value("${mockMarketPriceProvider.gbm.initialDate:2020-01-01}")
    private String initialDateStr;
    private Instant currentDate;
    private int updatePeriodSec = 2;

    @PostConstruct
    public void start() {
        // Initialize stock prices
        stockPrices.put("AAPL", 110.0); // Initial price for AAPL
        stockPrices.put("TELSA", 450.0); // Initial price for TELSA
        currentDate = Instant.parse(initialDateStr);
        // Schedule task to update market data
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::updatePrices, 0, updatePeriodSec, TimeUnit.SECONDS); // Update every 0.5 to 2 seconds
    }

    private void updatePrices() {
        currentDate = currentDate.plus(updatePeriodSec, TimeUnit.SECONDS.toChronoUnit());
        for (String ticker : stockPrices.keySet()) {
            double currentPrice = stockPrices.get(ticker);
            double mu = Math.random(); // Example expected return between 0 and 1
            double sigma = Math.random(); // Example annualized standard deviation between 0 and 1

            // Generate a random value from a standardized normal distribution
            double epsilon = random.nextGaussian();

            // Calculate new price using the formula
            double dtPrice = currentPrice * (mu * (dt / 7257600) + sigma * epsilon * Math.sqrt(dt / 7257600));
            log.info("ticker dtPrice "+ticker+" "+dtPrice);
            double newPrice = Math.floor((currentPrice + dtPrice)*100)/100;
            // Update stock price
            stockPrices.put(ticker, newPrice);

            System.out.println("Updated price for " + ticker + ": " + newPrice + " at " + currentDate.toString());
        }
    }
}
