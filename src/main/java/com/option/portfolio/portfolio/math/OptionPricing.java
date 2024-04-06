package com.option.portfolio.portfolio.math;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class OptionPricing {

    public static double calculateOptionPrice(double currentPrice, double strikePrice, double riskFreeRate, double volatility, Instant currentDate, Instant expirationDate, boolean isCall) {
        double t = calculateTimeToMaturity(currentDate, expirationDate);

        double d1 = (Math.log(currentPrice / strikePrice) + (riskFreeRate + 0.5 * volatility * volatility) * t) / (volatility * Math.sqrt(t));
        double d2 = d1 - volatility * Math.sqrt(t);

        double N_d1 = NormalDistribution.CumulativeProbability(d1);
        double N_d2 = NormalDistribution.CumulativeProbability(d2);

        if (isCall) {
            return currentPrice * N_d1 - strikePrice * Math.exp(-riskFreeRate * t) * N_d2;
        } else {
            return strikePrice * Math.exp(-riskFreeRate * t) * ( -1 * N_d2) - currentPrice * ( -1 * N_d1);
        }
    }

    private static double calculateTimeToMaturity(Instant currentDate, Instant expirationDate) {
        long daysToExpiration = ChronoUnit.DAYS.between(currentDate, expirationDate);
        return (double) daysToExpiration / 365; // Assuming 365days in a year
    }
}
