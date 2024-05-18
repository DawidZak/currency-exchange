package com.exchange.task.user.domain.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class PeselValidator implements ConstraintValidator<Pesel, String> {

    @Override
    public void initialize(Pesel constraintAnnotation) {
    }

    @Override
    public boolean isValid(String pesel, ConstraintValidatorContext context) {
        if (pesel == null || !pesel.matches("\\d{11}")) {
            return false;
        }

        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int sum = 0;

        for (int i = 0; i < 10; i++) {
            sum += (pesel.charAt(i) - '0') * weights[i];
        }

        int checkDigit = (10 - (sum % 10)) % 10;
        return checkDigit == (pesel.charAt(10) - '0');
    }
}