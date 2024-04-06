package com.option.portfolio.portfolio.repository;

import com.option.portfolio.portfolio.entity.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SecurityRepository extends JpaRepository<Security, Long> {

    @Query("SELECT s FROM Security s ORDER BY  s.symbol ASC, s.securityType.type DESC")
    List<Security> findAllOrderBySymbolAndSecurityType();

    @Query("SELECT s FROM Security s WHERE s.securityType.type <> 'STOCK' ORDER BY  s.symbol ASC, s.securityType.type DESC")
    List<Security> findNonStockTypeOrderBySymbolAndSecurityType();

    @Query("SELECT s FROM Security s WHERE s.securityType.type = 'STOCK' ORDER BY  s.symbol ASC, s.securityType.type DESC")
    List<Security> findStockTypeOrderBySymbolAndSecurityType();
}
