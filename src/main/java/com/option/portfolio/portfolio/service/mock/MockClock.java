package com.option.portfolio.portfolio.service.mock;

import com.option.portfolio.portfolio.service.Clock;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Profile({"mock"})
@Service
public class MockClock implements Clock {
    @Value("${mockMarketPriceProvider.gbm.initialDate:2020-01-01}")
    private String initialDateStr;

    private Instant now;

    public MockClock() {
    }
    @Override
    public Instant getNow() {
        return now;
    }

    @Override
    public Instant getInitDateTime(){
        return Instant.parse(initialDateStr);
    }
    @PostConstruct
    private void tikTok()
    {
        now = Instant.parse(initialDateStr);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(()->{
            now = now.plusMillis(50);
        }, 0, 50, TimeUnit.MILLISECONDS);
    }

}
