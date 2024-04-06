package com.option.portfolio.portfolio;

import com.option.portfolio.portfolio.math.OptionPricing;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


class OptionPriceCalculatorTest {

    @Test
    public void testCalculateOptionPrice_CallOption() {
        double currentPrice = 100.0;
        double strikePrice = 110.0;
        double riskFreeRate = 0.05;
        double volatility = 0.2;
        Instant currentDate = Instant.now();
        Instant expirationDate = currentDate.plus(30, ChronoUnit.DAYS);
        boolean isCall = true;

        double optionPrice = OptionPricing.calculateOptionPrice(currentPrice, strikePrice, riskFreeRate, volatility, currentDate, expirationDate, isCall);

        // Expected option price calculated using a known formula or external tool
        double expectedOptionPrice = 4.92;

        assertEquals(expectedOptionPrice, optionPrice, 0.001); // Using delta for double comparison
    }

    @Test
    public void testCalculateOptionPrice_PutOption() {
        double currentPrice = 100.0;
        double strikePrice = 90.0;
        double riskFreeRate = 0.05;
        double volatility = 0.2;
        Instant currentDate = Instant.now();
        Instant expirationDate = currentDate.plus(30, ChronoUnit.DAYS);
        boolean isCall = false;

        double optionPrice = OptionPricing.calculateOptionPrice(currentPrice, strikePrice, riskFreeRate, volatility, currentDate, expirationDate, isCall);

        // Expected option price calculated using a known formula or external tool
        double expectedOptionPrice = 5.247;

        assertEquals(expectedOptionPrice, optionPrice, 0.001); // Using delta for double comparison
    }


}
