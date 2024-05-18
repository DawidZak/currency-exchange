package com.exchange.task.exchange.api;

import com.exchange.task.user.domain.model.Currencies;
import com.exchange.task.user.domain.validator.Pesel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ExchangeCurrenciesRequest(@PositiveOrZero BigDecimal amount, @NotNull Currencies fromCurrency,
                                        @NotNull Currencies toCurrency, @Pesel String pesel) {
}
