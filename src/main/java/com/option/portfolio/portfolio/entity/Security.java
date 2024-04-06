package com.option.portfolio.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "security")
@EntityListeners(AuditingEntityListener.class)
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "version", columnDefinition = "Long DEFAULT 0")
    @Version
    @JsonIgnore
    Long version = 0L;


    @Column(name = "symbol", unique = true, nullable = false)
    String symbol;

    @Column(name = "ticker", nullable = false)
    String ticker;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "security_type_id", nullable = false)
    SecurityType securityType;

    //price for stock, strike for option
    @Column(name = "strike")
    Double strike;

    @Column(name = "maturity")
    Instant maturity;

    @Column(name = "mu", nullable = false)
    Double mu;

    @Column(name = "volatility", nullable = false)
    Double volatility;

    @Column(name = "price", nullable = false)
    Double price;

    @Column(name = "position", nullable = false)
    Integer position;

    @CreationTimestamp
    @Column(name = "date_created")
    LocalDateTime dateCreated;

    @UpdateTimestamp
    @Column(name = "last_updated")
    LocalDateTime lastUpdated;

    public Security() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public SecurityType getSecurityType() {
        return securityType;
    }

    public void setSecurityType(SecurityType securityType) {
        this.securityType = securityType;
    }

    public Double getStrike() {
        return strike;
    }

    public void setStrike(Double strike) {
        this.strike = strike;
    }

    public Instant getMaturity() {
        return maturity;
    }

    public void setMaturity(Instant maturity) {
        this.maturity = maturity;
    }

    public Double getMu() {
        return mu;
    }

    public void setMu(Double mu) {
        this.mu = mu;
    }

    public Double getVolatility() {
        return volatility;
    }

    public void setVolatility(Double volatility) {
        this.volatility = volatility;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
