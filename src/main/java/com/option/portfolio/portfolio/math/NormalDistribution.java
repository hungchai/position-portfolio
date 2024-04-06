package com.option.portfolio.portfolio.math;

public class NormalDistribution {
    public static double CumulativeProbability(double x) {
        int n = 1000; // Number of intervals for numerical integration
        double sum = 0.0;
        double delta = x / n;

        for (int i = 0; i < n; i++) {
            double xi = i * delta;
            sum += standardNormalPDF(xi) * delta;
        }

        return sum;
    }

    // Probability density function (PDF) for the standard normal distribution
    private static double standardNormalPDF(double x) {
        return Math.exp(-x * x / 2) / Math.sqrt(2 * Math.PI);
    }

}
