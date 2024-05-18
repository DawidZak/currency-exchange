package com.exchange.task.exchange.infrastructure;

import com.exchange.task.exchange.domain.model.Currency;
import com.exchange.task.exchange.domain.model.Rate;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
class CurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CODE", nullable = false)
    private String code;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "BID", nullable = false)
    private BigDecimal bid;
    @Column(name = "ASK", nullable = false)
    private BigDecimal ask;
    @Column(name = "EFFECTIVE_DATE", nullable = false)
    private LocalDate effectiveDate;

    public CurrencyEntity() {
    }

    public CurrencyEntity(BigDecimal ask, BigDecimal bid, String code, String description, LocalDate effectiveDate) {
        this.ask = ask;
        this.bid = bid;
        this.code = code;
        this.description = description;
        this.effectiveDate = effectiveDate;

    }

    //TODO test
    public static CurrencyEntity fromDomain(Currency currency) {
        return new CurrencyEntity(currency.rate().ask(),
                currency.rate().bid(), currency.code(),
                currency.description(),
                currency.rate().effectiveDate());
    }

    //TODO test
    public static Currency toDomain(CurrencyEntity currencyEntity) {
        return new Currency(currencyEntity.getCode(),
                currencyEntity.getDescription(),
                new Rate(currencyEntity.getBid(),
                        currencyEntity.getAsk(),
                        currencyEntity.getEffectiveDate()));
    }
}
