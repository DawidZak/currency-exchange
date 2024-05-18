package com.exchange.task.user.api;

import com.exchange.task.user.domain.UserUnderAgeException;
import com.exchange.task.user.domain.validator.Pesel;
import com.exchange.task.user.domain.validator.UserAgeBasedOnPeselValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateUserRequest(@NotBlank String firstName,
                                @NotBlank String lastName,
                                @Pesel String pesel,
                                @PositiveOrZero BigDecimal initialAccountBalanceInPLN) {

    public CreateUserRequest(String firstName, String lastName, String pesel, BigDecimal initialAccountBalanceInPLN) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.initialAccountBalanceInPLN = initialAccountBalanceInPLN;

        if (!UserAgeBasedOnPeselValidator.isAdult(pesel)) {
            throw new UserUnderAgeException("User too young, required age >= 18");
        }

    }
}
