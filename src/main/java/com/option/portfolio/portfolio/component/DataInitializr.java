package com.option.portfolio.portfolio.component;

import com.option.portfolio.portfolio.service.DataInitializrService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializr {

    private final DataInitializrService dataInitializrService;

    public DataInitializr(DataInitializrService dataInitializrService) {
        this.dataInitializrService = dataInitializrService;
    }

    @PostConstruct
    public void init() {
        dataInitializrService.buildInitData();
    }


}
