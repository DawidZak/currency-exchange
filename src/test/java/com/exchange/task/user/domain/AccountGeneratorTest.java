package com.exchange.task.user.domain;

import com.exchange.task.user.domain.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class AccountGeneratorTest {

    @Test
    void shouldGenerateAccountBasedOnParameters() {
        //given
        String currency = "USD";
        //when
        Account generated = AccountGenerator.generate(currency, BigDecimal.ZERO);
        //then
        Assertions.assertEquals(BigDecimal.ZERO, generated.getBalance());
        Assertions.assertEquals(currency, generated.getCurrency());

    }
}