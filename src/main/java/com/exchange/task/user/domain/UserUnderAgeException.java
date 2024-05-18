package com.exchange.task.user.domain;

public class UserUnderAgeException extends RuntimeException {

    public UserUnderAgeException(String message) {
        super(message);
    }
}
