package com.exchange.task.user.domain;

import com.exchange.task.user.api.CreateUserRequest;
import com.exchange.task.user.domain.model.Account;
import com.exchange.task.user.domain.model.Currencies;
import com.exchange.task.user.domain.model.User;
import com.exchange.task.user.domain.validator.UserAgeBasedOnPeselValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO use case? CreateUserService, UpdateUserService, UpdateUserAccount,
public class WriteUserService {

    private final UserPort userPort;

    @Transactional
    public User createUser(CreateUserRequest createUserRequest) {
        if (userPort.userExists(createUserRequest.pesel())) {
            throw new UserAlreadyExists("User with given PESEL exists");
        }

        if (!UserAgeBasedOnPeselValidator.isAdult(createUserRequest.pesel())) {
            throw new UserUnderAgeException("User too young, required age >= 18");
        }


        Account plnAccount = AccountGenerator.generate(Currencies.PLN.name(), createUserRequest.initialAccountBalanceInPLN());
        Account usdAccount = AccountGenerator.generate(Currencies.USD.name(), BigDecimal.ZERO);
        User user = new User(createUserRequest.firstName(), createUserRequest.lastName(), createUserRequest.pesel(), List.of(plnAccount, usdAccount));
        User savedUser = userPort.saveUser(user);
        log.info("Account with balance {} and currency {} created", plnAccount.getBalance(), plnAccount.getCurrency());
        log.info("Account with balance {} and currency {} created", usdAccount.getBalance(), usdAccount.getCurrency());
        return savedUser;
    }

    @Transactional
    public void updateUser(User user) {
        userPort.saveUser(user);
        log.info("User {} balance updated", user.getPesel());
    }
}
