package com.exchange.task.exchange.infrastructure;

import com.exchange.task.exchange.domain.model.Currency;
import com.exchange.task.exchange.domain.model.Rate;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
class NbpIntegration {
    private static final Logger log = LoggerFactory.getLogger(NbpIntegration.class);
//https://kantoralex.pl/blog/jak-czytac-i-przeliczac-kursy-walut-2/

    @Qualifier("currencyApi")
    private final RestClient restClient;

    private final CurrencyRepository currencyRepository;
    private final CircuitBreakerFactory cbFactory;

    public NbpIntegration(RestClient restClient, CurrencyRepository currencyRepository, CircuitBreakerFactory cbFactory) {
        this.restClient = restClient;
        this.currencyRepository = currencyRepository;
        this.cbFactory = cbFactory;
    }

    public Currency getCurrencyRate(String currencyCode) {
        Currency currency = cbFactory.create("currency")
                .run(() -> getCurrencyRateFromNbp(currencyCode), throwable -> {
                    log.info("Fallback from previous currency rate");
                    return CurrencyEntity.toDomain(currencyRepository.getLatestCurrency(currencyCode)
                            .orElseThrow(() -> new CurrencyRateNotFoundException(String.format("Currency rate for %s not found", currencyCode))));
                });
        currencyRepository.save(CurrencyEntity.fromDomain(currency));
        return currency;


    }

    private Currency getCurrencyRateFromNbp(String currencyCode) {
        CurrencyResponse result = restClient.get().uri("/exchangerates/rates/c/{currencyCode}/today/", currencyCode)
                .retrieve()
                .body(CurrencyResponse.class);
        return new Currency(result.getCode(), result.getDescription(), result.getRates().getFirst());
    }

    @Setter
    @Getter
    private static class CurrencyResponse {
        private String description;
        private String code;
        private List<Rate> rates;

    }


}
