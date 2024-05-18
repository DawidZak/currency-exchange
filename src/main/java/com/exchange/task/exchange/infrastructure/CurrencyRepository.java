package com.exchange.task.exchange.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {

    @Query("from CurrencyEntity where effectiveDate = current_date and code = :currencyCode")
    Optional<CurrencyEntity> getTodayCurrency(String currencyCode);

    @Query("from CurrencyEntity where code = :currencyCode order by effectiveDate desc")
    Optional<CurrencyEntity> getLatestCurrency(String currencyCode);
}
