package com.exchange.task.user.domain;

import com.exchange.task.exchange.api.ExchangeCurrenciesRequest;
import com.exchange.task.exchange.domain.model.Rate;
import com.exchange.task.user.domain.model.Account;
import com.exchange.task.user.domain.model.Currencies;
import com.exchange.task.user.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

class UserTest {

    private static final String PESEL = "111111111111";
//TODO parametrized test
    @Test
    void shouldExchangePlnToUsd() {
        //given
        Account usdAccount = Account.builder()
                .number("123")
                .currency("USD")
                .balance(BigDecimal.ZERO)
                .build();

        Account plnAccount = Account.builder()
                .number("123")
                .currency("PLN")
                .balance(BigDecimal.valueOf(100))
                .build();

        ExchangeCurrenciesRequest exchangeCurrenciesRequest = new ExchangeCurrenciesRequest(BigDecimal.TEN, Currencies.PLN, Currencies.USD, PESEL);
        User user = user(plnAccount, usdAccount);
        //when
        User exchanged = user.exchange(exchangeCurrenciesRequest, new Rate(BigDecimal.valueOf(5), BigDecimal.valueOf(5), LocalDate.now()));
        //TODO map
        //then
        Account updatedAccountUsdAccount = exchanged.getAccounts().stream().filter(account -> account.getCurrency().equals("USD")).findAny().get();
        Assertions.assertEquals(BigDecimal.valueOf(2).setScale(2, RoundingMode.DOWN), updatedAccountUsdAccount.getBalance());
    }

    @Test
    void shouldExchangeUsdToPln() {
        //given
        Account usdAccount = Account.builder()
                .number("123")
                .currency("USD")
                .balance(BigDecimal.valueOf(100))
                .build();

        Account plnAccount = Account.builder()
                .number("123")
                .currency("PLN")
                .balance(BigDecimal.ZERO)
                .build();

        ExchangeCurrenciesRequest exchangeCurrenciesRequest = new ExchangeCurrenciesRequest(BigDecimal.valueOf(20), Currencies.USD, Currencies.PLN, PESEL);
        User user = user(plnAccount, usdAccount);
        //when
        User exchanged = user.exchange(exchangeCurrenciesRequest, new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(5), LocalDate.now()));
        //TODO map
        //then
        Account updatedAccountPlnAccount = exchanged.getAccounts().stream().filter(account -> account.getCurrency().equals("PLN")).findAny().get();
        Assertions.assertEquals(BigDecimal.valueOf(200).setScale(2, RoundingMode.DOWN), updatedAccountPlnAccount.getBalance());
    }

    private static User user(Account plnAccount, Account usdAccount) {
        return new User("Domnik", "X", PESEL, List.of(plnAccount, usdAccount));
    }

}