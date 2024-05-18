package com.exchange.task.user.domain.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UserAgeBasedOnPeselValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = { "06240936628", "00240936664", "02040924469","93040976648" })
    void shouldRetrunTrueWhenPersonIsAdult(String pesel) {
        assertTrue(UserAgeBasedOnPeselValidator.isAdult(pesel), pesel + " should be considered as adult");
    }

    @ParameterizedTest
    @ValueSource(strings = { "18240962152", "06270945432", "25270954282" })
    void shouldReturnFalseWhenPersonIsMinor(String pesel) {
        assertFalse(UserAgeBasedOnPeselValidator.isAdult(pesel), pesel + " should be considered as minor");
    }

    @Test
    void shouldThrowExceptionWhenPeselIsIncorrect() {
        assertThrows(IllegalArgumentException.class, () -> UserAgeBasedOnPeselValidator.isAdult("invalid_pesel"));
    }
}