package com.exchange.task.user.domain.model;

import com.exchange.task.exchange.api.ExchangeCurrenciesRequest;
import com.exchange.task.exchange.domain.model.Rate;
import lombok.Data;
import lombok.Getter;

import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

@Data
public final class User {
    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private String pesel;
    private List<Account> accounts;

    public User(String firstName,
                String lastName,
                String pesel,
                List<Account> accounts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.accounts = accounts;
    }

    public User exchange(ExchangeCurrenciesRequest exchangeCurrenciesRequest, Rate rate) {
        //TODO exception, to method,
        Account from = accounts.stream().
                filter(account -> account.getCurrency().equals(exchangeCurrenciesRequest.fromCurrency().name()))
                .findAny()
                .orElseThrow();
        Account to = accounts.stream()
                .filter(account -> account.getCurrency().equals(exchangeCurrenciesRequest.toCurrency().name()))
                .findAny()
                .orElseThrow();
        //from PLN to USD
        if ("PLN".equals(from.getCurrency())) {
            from = from.withBalance(from.getBalance().subtract(exchangeCurrenciesRequest.amount()));
            to = to.withBalance(exchangeCurrenciesRequest.amount().setScale(2, RoundingMode.DOWN).divide(rate.ask(), RoundingMode.DOWN));
        } else {
            from = from.withBalance(from.getBalance().subtract(exchangeCurrenciesRequest.amount()));
            to = to.withBalance(exchangeCurrenciesRequest.amount().multiply(rate.bid()).setScale(2, RoundingMode.DOWN).add(to.getBalance()));
        }
        //TODO <currency, account>
        List<Account> updatedAccounts = accounts.stream()
                .dropWhile(account -> account.getCurrency().equals("PLN") || account.getCurrency().equals("USD")).toList();
        accounts = Stream.concat(updatedAccounts.stream(), Stream.of(from, to)).toList();
        return this;

    }

    public List<Account> getAccounts() {
        return List.copyOf(accounts);
    }

}