package com.exchange.task.exchange.infrastructure;

import com.exchange.task.exchange.domain.CurrencyPort;
import com.exchange.task.exchange.domain.model.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class CurrencyPortImpl implements CurrencyPort {

    private final CurrencyRepository currencyRepository;
    private final CircuitBreakerFactory cbFactory;
    private final NbpIntegration currencyRatesPort;


    @Override
    public Optional<Currency> getTodayCurrency(String currencyCode) {
        return Optional.of(currencyRepository.getTodayCurrency(currencyCode)
                .map(CurrencyEntity::toDomain)
                .orElseGet(() -> {
                    return cbFactory.create("currency")
                            .run(() -> currencyRatesPort.getCurrencyRate(currencyCode), throwable -> {
                                return currencyRepository.getLatestCurrency(currencyCode)
                                        .map(CurrencyEntity::toDomain)
                                        .orElseThrow();
                                //TODO exception
                            });
                }));
    }
}
