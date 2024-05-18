package com.exchange.task.user.domain.model;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class Account {
    @With
    UUID id;
    String number;
    String currency;
    @With
    BigDecimal balance;

}
