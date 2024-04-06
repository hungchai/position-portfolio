package com.option.portfolio.portfolio.eventListener;

import com.option.portfolio.portfolio.cache.CacheStore;
import com.option.portfolio.portfolio.entity.Security;
import com.option.portfolio.portfolio.entity.dto.SecurityDto;
import com.option.portfolio.portfolio.entity.event.PriceUpdate;
import com.option.portfolio.portfolio.entity.event.PriceUpdateEvent;
import com.option.portfolio.portfolio.component.mock.MockMarketDataProvider;
import com.option.portfolio.portfolio.math.OptionPricing;
import com.option.portfolio.portfolio.service.Clock;
import com.option.portfolio.portfolio.service.SecurityDataService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class PortfolioResultSubscriber {
    Logger log = LoggerFactory.getLogger(MockMarketDataProvider.class);

    private final CacheStore<SecurityDto> securityCache;

    @Value("${mockMarketPriceProvider.riskFreeInterestRate:0.02}")
    private double riskFreeInterestRate;

    private final SecurityDataService securityDataService;

    private final Clock clock;
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    String valueFormat = "$%,.2f";

    private List<Security> optionSecurityList;
    private final List<String> securitySymbolList = new ArrayList<>();

    public PortfolioResultSubscriber(SecurityDataService securityDataService, CacheStore<SecurityDto> securityCache, Clock clock) {
        this.securityDataService = securityDataService;
        this.securityCache = securityCache;
        this.clock = clock;
    }

    @PostConstruct
    public void init() {
        log.info("PriceUpdateListener initialized");
        initalizeMetaData();
        initializeOptionPriceJob();
    }


    @EventListener
    public void priceUpdateListener(PriceUpdateEvent event) {
        StringBuilder sb = new StringBuilder();
        for (PriceUpdate priceUpdate : event.getPriceUpdates()) {
            sb.append(priceUpdate.getSymbol()).append(" change to ").append(priceUpdate.getStockPrice()).append(" at ").append(priceUpdate.getPriceTimestamp().toString()).append("\n");
            var stockDto = securityCache.get(priceUpdate.getSymbol());
            stockDto.setPrice(priceUpdate.getStockPrice());
            stockDto.setPriceTimestamp(priceUpdate.getPriceTimestamp());
        }
        System.out.println(sb);
        prettyPrint();
    }

    private void prettyPrint()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("## portfolio \n");
        sb.append(String.format("%-30s%30s%30s%30s%n", "Symbol", "Price", "Qty", "Value"));
        AtomicReference<Double> total = new AtomicReference<>((double) 0);
        securitySymbolList.forEach(symbol -> {
            var securityDto = securityCache.get(symbol);
            final double price = securityDto.getPrice();
            final Double value = price * securityDto.getPosition();
            total.updateAndGet(v -> v + value);
            sb.append(String.format("%-30s%30.2f%30f%30s%n", symbol, price, securityDto.getPosition(), String.format(valueFormat, value)));
        });

        sb.append("\n");
        sb.append(String.format("%-30s%91s%n", "#Total portfolio", String.format("$%,.2f%n", total.get())));

        sb.append("\n");
        sb.append("\n");
        System.out.println(sb);
    }

    private void initalizeMetaData() {
        //load the securityList from the database
        optionSecurityList = securityDataService.findNonStockOrderBySymbolAndSecurityType();
        List<Security> securityList  = securityDataService.findAllOrderBySymbolAndSecurityType();
        securityList.forEach(security -> {
                    securitySymbolList.add(security.getSymbol());
                    securityCache.add(security.getSymbol(), new SecurityDto(
                                    security.getSymbol(),
                                    security.getPrice(),
                                    security.getPosition(),
                                    this.clock.getNow()
                            )
                    );
                }
        );
    }

    private void initializeOptionPriceJob() {
        executor.scheduleAtFixedRate(() -> {
            optionSecurityList.forEach(option -> {
                String symbol = option.getSymbol();
                String ticker = option.getTicker();
                var stockDto = securityCache.get(ticker);
                double currentPrice = stockDto.getPrice();
                double optionPrice = OptionPricing.calculateOptionPrice(currentPrice, option.getStrike(), riskFreeInterestRate, option.getVolatility(), clock.getNow(), option.getMaturity(), option.getSecurityType().getType().equals("CALL"));
                optionPrice = Math.floor(optionPrice * 1000) / 1000;
                log.debug("Option price for " + symbol + ": " + optionPrice + " at " + clock.getNow().toString());
                var optionaDto = securityCache.get(symbol);
                optionaDto.setPrice(optionPrice);
                optionaDto.setPriceTimestamp(this.clock.getNow());
            });
        }, 100,500, TimeUnit.MILLISECONDS);
    }

}
