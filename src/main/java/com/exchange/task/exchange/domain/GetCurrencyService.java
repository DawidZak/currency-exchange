package com.exchange.task.exchange.domain;

import com.exchange.task.exchange.domain.model.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCurrencyService {


    private final CurrencyPort currencyPort;

    public Currency getCurrencyRate(String currencyCode) {
        //TODO custom exception
        return currencyPort.getTodayCurrency(currencyCode).orElseThrow();


    }

}
