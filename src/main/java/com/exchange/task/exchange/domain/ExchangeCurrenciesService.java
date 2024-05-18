package com.exchange.task.exchange.domain;


import com.exchange.task.exchange.api.ExchangeCurrenciesRequest;
import com.exchange.task.exchange.domain.model.Rate;
import com.exchange.task.user.domain.WriteUserService;
import com.exchange.task.user.domain.GetUserService;
import com.exchange.task.user.domain.model.Currencies;
import com.exchange.task.user.domain.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeCurrenciesService {

    private final GetUserService getUserService;
    private final WriteUserService writeUserService;
    private final GetCurrencyService getCurrencyService;

    @Transactional
    public void makeExchange(ExchangeCurrenciesRequest exchangeCurrenciesRequest) {
        log.info("Started exchange transaction for pesel {}", exchangeCurrenciesRequest.pesel());
        Rate rate = getCurrencyService.getCurrencyRate(Currencies.USD.name()).rate();
        log.info("Currency rate ask {} bid {} based on day {}", rate.ask(), rate.bid(), rate.effectiveDate());
        User user = getUserService.getUserById(exchangeCurrenciesRequest.pesel());
        user.exchange(exchangeCurrenciesRequest, rate);
        writeUserService.updateUser(user);
        log.info("Successfully finished transaction");
    }
}
