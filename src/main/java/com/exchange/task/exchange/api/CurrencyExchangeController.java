package com.exchange.task.exchange.api;

import com.exchange.task.exchange.domain.ExchangeCurrenciesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    private final ExchangeCurrenciesService exchangeCurrenciesService;

    public CurrencyExchangeController(ExchangeCurrenciesService exchangeCurrenciesService) {
        this.exchangeCurrenciesService = exchangeCurrenciesService;
    }

    //TODO swagger
    @PostMapping("/exchange/convert")
    public void exchangeCurrencies(@RequestBody ExchangeCurrenciesRequest exchangeCurrenciesRequest) {
        exchangeCurrenciesService.makeExchange(exchangeCurrenciesRequest);
    }
}
