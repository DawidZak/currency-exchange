package com.exchange.task.exchange.domain;

import com.exchange.task.exchange.domain.model.Currency;

import java.util.Optional;

public interface CurrencyPort {

    Optional<Currency> getTodayCurrency(String currencyCode);
}
