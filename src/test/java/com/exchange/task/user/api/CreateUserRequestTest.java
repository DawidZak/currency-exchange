package com.exchange.task.user.api;

import com.exchange.task.user.domain.UserUnderAgeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserRequestTest {

    @Test
    void shouldThrowExceptionWhenUserIsMinor() {
        //expects
        Assertions.assertThrows(UserUnderAgeException.class, () -> {
            CreateUserRequest createUserRequest = new CreateUserRequest("D", "A", "18240962152", BigDecimal.ZERO);
        });
    }
}