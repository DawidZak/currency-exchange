package com.exchange.task.user.infrastructure;

import com.exchange.task.user.domain.model.User;
import com.exchange.task.user.domain.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class UserPortImpl implements UserPort {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id).map(UserEntity::toDomain);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(UserEntity.fromDomain(user)).toDomain();
    }

    @Override
    public boolean userExists(String pesel) {
        return userRepository.existsById(pesel);
    }
}
