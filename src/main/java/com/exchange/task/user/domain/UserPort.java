package com.exchange.task.user.domain;

import com.exchange.task.user.domain.model.User;

import java.util.Optional;

public interface UserPort {

    Optional<User> findById(String id);

    User saveUser(User user);

    boolean userExists(String pesel);
}
