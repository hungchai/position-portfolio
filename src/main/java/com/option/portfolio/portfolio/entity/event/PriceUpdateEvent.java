package com.option.portfolio.portfolio.entity.event;

import org.springframework.context.ApplicationEvent;

import java.util.List;

public class PriceUpdateEvent extends ApplicationEvent {
    private final List<PriceUpdate> priceUpdates;

    public PriceUpdateEvent(Object source, List<PriceUpdate> priceUpdates) {
        super(source);
        this.priceUpdates = priceUpdates;
    }

    public List<PriceUpdate> getPriceUpdates() {
        return priceUpdates;
    }

}
