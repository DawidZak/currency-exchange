package com.exchange.task.user.domain;

import com.exchange.task.user.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class GetUserService {

    private final UserPort userPort;

    public GetUserService(UserPort userRepository) {
        this.userPort = userRepository;
    }

    public User getUserById(String pesel) {
        return userPort.findById(pesel)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with pesel  %s not found", pesel)));

    }
}
