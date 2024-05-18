package com.exchange.task.user.domain;

import com.exchange.task.user.domain.model.Account;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Random;

@Slf4j
@UtilityClass
class AccountGenerator {
    private static final Random random = new Random();


    public static Account generate(String currency, BigDecimal balance) {
        String accountNumber = String.valueOf(random.nextInt());
        Account account = Account.builder()
                .number(accountNumber)
                .currency(currency)
                .balance(balance)
                .build();

        log.info("Account with number {} and currency {} generated", accountNumber, currency);
        return account;
    }
}
