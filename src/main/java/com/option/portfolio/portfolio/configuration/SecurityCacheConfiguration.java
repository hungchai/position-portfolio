package com.option.portfolio.portfolio.configuration;

import com.option.portfolio.portfolio.cache.CacheStore;
import com.option.portfolio.portfolio.entity.dto.SecurityDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityCacheConfiguration {

    @Bean
    public CacheStore<SecurityDto> securityCache() {
        return new CacheStore<>();
    }
}
