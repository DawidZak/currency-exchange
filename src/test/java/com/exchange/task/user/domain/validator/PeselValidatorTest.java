package com.exchange.task.user.domain.validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PeselValidatorTest {

    private final PeselValidator validator = new PeselValidator();

    @ParameterizedTest
    @CsvSource({
            "44051401359, true, valid PESEL",
            "02070803628, true, valid PESEL",

            "4405140135, false, incorrect length",
            "440514013591, false, incorrect length",

            "4405140135A, false, non-numeric character",

            "44051401358, false, invalid check digit",
            "02070803629, false, invalid check digit",

    })
    void shouldValidatePesel(String pesel, boolean expectedResult, String description) {
        boolean isValid = validator.isValid(pesel, null);
        assertEquals(expectedResult, isValid, description);
    }

    @ParameterizedTest
    @NullSource
    void shouldValidatePeselForNullValues(String pesel) {
        boolean isValid = validator.isValid(pesel, null);
        assertFalse(isValid);
    }

    @ParameterizedTest
    @EmptySource
    void shouldValidatePeselForEmptyValues(String pesel) {
        boolean isValid = validator.isValid(pesel, null);
        assertFalse(isValid);
    }
}