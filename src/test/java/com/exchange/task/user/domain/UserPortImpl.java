package com.exchange.task.user.domain;

import com.exchange.task.user.domain.model.User;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class UserPortImpl implements UserPort {

    private final ConcurrentHashMap<String, User> db = new ConcurrentHashMap<>();


    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public User saveUser(User user) {
        return db.put(user.getPesel(),user);
    }

    @Override
    public boolean userExists(String pesel) {
        return db.containsKey(pesel);
    }
}
