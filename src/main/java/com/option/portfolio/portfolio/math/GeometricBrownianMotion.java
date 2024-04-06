package com.option.portfolio.portfolio.math;

import java.util.Random;

public class GeometricBrownianMotion {
    static double magnifier  = 10;
    public static double GeometricBrownianMotionForStockPrice (double currentPrice, double mu, double volatility, double dt){
        double epsilon = (new Random()).nextGaussian();
        return magnifier * (currentPrice * ((mu * (dt / 7257600) + volatility * epsilon * Math.sqrt(dt / 7257600))));
    }
}
