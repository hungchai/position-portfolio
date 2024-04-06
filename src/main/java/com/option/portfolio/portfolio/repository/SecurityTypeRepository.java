package com.option.portfolio.portfolio.repository;

import com.option.portfolio.portfolio.entity.SecurityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityTypeRepository extends JpaRepository<SecurityType, Long> {

    SecurityType findByType(String type);
}
