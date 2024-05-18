package com.exchange.task.exchange.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Rate(BigDecimal bid, BigDecimal ask, LocalDate effectiveDate) {

}
