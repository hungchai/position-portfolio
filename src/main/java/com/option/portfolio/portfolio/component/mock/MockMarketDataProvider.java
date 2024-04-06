package com.option.portfolio.portfolio.component.mock;

import com.option.portfolio.portfolio.entity.Security;
import com.option.portfolio.portfolio.entity.event.PriceUpdate;
import com.option.portfolio.portfolio.entity.event.PriceUpdateEvent;
import com.option.portfolio.portfolio.math.GeometricBrownianMotion;
import com.option.portfolio.portfolio.service.Clock;
import com.option.portfolio.portfolio.service.SecurityDataService;
import jakarta.annotation.PostConstruct;
import org.antlr.v4.runtime.misc.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@DependsOn({"dataInitializr"})
@Profile({"mock"})
public class MockMarketDataProvider {
    Logger log = LoggerFactory.getLogger(MockMarketDataProvider.class);

    private final SecurityDataService securityDataService;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final Clock clock;

    @Value("${mockMarketPriceProvider.gbm.priceJobDelay:120}")
    private double priceJobDelay;

    private double dt = priceJobDelay;
    private final Random random = new Random();

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

    List<PriceUpdate> initPriceUpdateList = new ArrayList<>();

    private final Map<String, Double> stockPrices = new HashMap<>();
    private final Map<String, Pair<Double, Double>> stockMuVolatility = new HashMap<>();
    List<Security> securityList;

    List<Security> stockSecurityList = new ArrayList<>();

    public MockMarketDataProvider(SecurityDataService securityDataService, Clock clock, ApplicationEventPublisher applicationEventPublisher){
        this.securityDataService = securityDataService;
        this.clock = clock;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostConstruct
    public void start(){
        // Initialize stock prices
        initializeSecurities();

        //init price update event
        scheduleInitialPriceUpdateEvent();

        // Schedule task to update market data
        schedulePriceUpdates();
    }

    private void initializeSecurities(){
        securityList = securityDataService.findStockOrderBySymbolAndSecurityType();

        securityList.forEach(security -> {
            stockSecurityList.add(security);
            stockPrices.put(security.getTicker(), security.getPrice());
            stockMuVolatility.put(security.getSymbol(), new Pair<>(security.getMu(), security.getVolatility()));
            initPriceUpdateList.add(new PriceUpdate(security.getTicker(), security.getPrice(), clock.getInitDateTime()));
        });
    }
    private void scheduleInitialPriceUpdateEvent(){
        executor.schedule(() -> applicationEventPublisher.publishEvent(new PriceUpdateEvent(this, initPriceUpdateList)), 2, TimeUnit.SECONDS);
    }

    private void schedulePriceUpdates(){
        executor.scheduleAtFixedRate(this::startUpdatingPrices, 3, 500, TimeUnit.MILLISECONDS); // Update every 0.5 to 2 seconds
    }

    public void startUpdatingPrices() {
        priceJobDelay = random.nextDouble(priceJobDelay+0.5);
        dt = priceJobDelay;
        executor.schedule(this::updatePrices, (int) priceJobDelay, TimeUnit.SECONDS);
    }

    private void updatePrices() {
        for (String ticker : stockPrices.keySet()) {
            double currentPrice = stockPrices.get(ticker);
            double mu = stockMuVolatility.get(ticker).a; // Example expected return between 0 and 1
            double volatility = stockMuVolatility.get(ticker).b; // Example annualized standard deviation between 0 and 1
            List<PriceUpdate> priceUpdateList = new ArrayList<>();

            // Calculate new price using the formula ,7257600 is around 3 months in seconds
            double dtPrice = GeometricBrownianMotion.GeometricBrownianMotionForStockPrice(currentPrice, mu, volatility, dt);
            log.debug("ticker dtPrice " + ticker + " " + dtPrice);
            double tempPrice = Math.floor((currentPrice + dtPrice) * 100) / 100;
            final double newPrice;
            if (tempPrice <= 0) {
                newPrice = currentPrice;
            } else {
                newPrice = tempPrice;
            }

            Instant priceTimestamp = clock.getNow();
            // Update stock price
            stockPrices.put(ticker, newPrice);
            priceUpdateList.add(new PriceUpdate(ticker, newPrice, priceTimestamp));

            applicationEventPublisher.publishEvent(new PriceUpdateEvent(this, priceUpdateList));

            log.debug("Updated price for " + ticker + ": " + newPrice + " at " + priceTimestamp.toString());
        }
    }
}
