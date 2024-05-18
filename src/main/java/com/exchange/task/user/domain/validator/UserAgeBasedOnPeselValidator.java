package com.exchange.task.user.domain.validator;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.Period;

@UtilityClass
public class UserAgeBasedOnPeselValidator {


    public static boolean isAdult(String pesel) {
        return getAgeFromPesel(pesel) >= 18;

    }

    private static int getAgeFromPesel(String pesel) {
        int year = Integer.parseInt(pesel.substring(0, 2));
        int month = Integer.parseInt(pesel.substring(2, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));

        // Determine the century and correct the year and month accordingly
        if (month >= 1 && month <= 12) {
            year += 1900;
        } else if (month >= 21 && month <= 32) {
            year += 2000;
            month -= 20;
        } else if (month >= 41 && month <= 52) {
            year += 2100;
            month -= 40;
        } else if (month >= 61 && month <= 72) {
            year += 2200;
            month -= 60;
        } else if (month >= 81 && month <= 92) {
            year += 1800;
            month -= 80;
        } else {
            throw new IllegalArgumentException("Invalid month in PESEL");
        }

        LocalDate birthDate = LocalDate.of(year, month, day);
        LocalDate currentDate = LocalDate.now();

        return Period.between(birthDate, currentDate).getYears();
    }
}
