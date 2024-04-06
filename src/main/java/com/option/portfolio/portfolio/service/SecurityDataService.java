package com.option.portfolio.portfolio.service;

import com.option.portfolio.portfolio.component.mock.MockMarketDataProvider;
import com.option.portfolio.portfolio.entity.Security;
import com.option.portfolio.portfolio.repository.SecurityRepository;
import com.option.portfolio.portfolio.entity.SecurityType;
import com.option.portfolio.portfolio.repository.SecurityTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityDataService {
    Logger log = LoggerFactory.getLogger(MockMarketDataProvider.class);
    private final SecurityRepository securityRepository;
    private final SecurityTypeRepository securityTypeRepository;

    public SecurityDataService(SecurityRepository securityRepository, SecurityTypeRepository securityTypeRepository) {
        this.securityRepository = securityRepository;
        this.securityTypeRepository = securityTypeRepository;
    }

    public SecurityType getStockType() {
        return securityTypeRepository.findByType("STOCK");
    }

    public List<Security> findAllOrderBySymbolAndSecurityType() {
        return securityRepository.findAllOrderBySymbolAndSecurityType();
    }

    public List<Security> findNonStockOrderBySymbolAndSecurityType() {
        return securityRepository.findNonStockTypeOrderBySymbolAndSecurityType();
    }

    public List<Security> findStockOrderBySymbolAndSecurityType() {
        return securityRepository.findStockTypeOrderBySymbolAndSecurityType();
    }

}
