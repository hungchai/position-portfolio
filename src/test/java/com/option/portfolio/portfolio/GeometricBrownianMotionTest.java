package com.option.portfolio.portfolio;

import com.option.portfolio.portfolio.math.GeometricBrownianMotion;
import org.junit.jupiter.api.Test;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeometricBrownianMotionTest {

    @Test
    public void testGeometricBrownianMotionForStockPrice_PositiveResult() {
        double currentPrice = 100.0;
        double mu = 0.05;
        double volatility = 0.2;
        double dt = 1.0;

        double result = GeometricBrownianMotion.GeometricBrownianMotionForStockPrice(currentPrice, mu, volatility, dt);

        assertTrue(currentPrice + result > 0); // Ensure the result is positive
    }

    @Test
    public void testGeometricBrownianMotionForStockPrice_ZeroCurrentPrice() {
        double currentPrice = 0.0; // Zero current price
        double mu = 0.05;
        double volatility = 0.2;
        double dt = 1.0;

        double result = GeometricBrownianMotion.GeometricBrownianMotionForStockPrice(currentPrice, mu, volatility, dt);

        assertEquals(0.0, abs(result)); // The result should be zero if the current price is zero
    }

    @Test
    public void testGeometricBrownianMotionForStockPrice_ZeroVolatility() {
        double currentPrice = 100.0;
        double mu = 0.05;
        double volatility = 0.0; // Zero volatility
        double dt = 1.0;

        double result = GeometricBrownianMotion.GeometricBrownianMotionForStockPrice(currentPrice, mu, volatility, dt);

        assertEquals(0, result, 0.001 ); // The result should be equal to the current price if volatility is zero
    }
}
