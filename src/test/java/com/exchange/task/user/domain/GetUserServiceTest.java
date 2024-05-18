package com.exchange.task.user.domain;

import com.exchange.task.user.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class GetUserServiceTest {

    private final UserPort userPort = new UserPortImpl();
    private final GetUserService getUserService = new GetUserService(userPort);


    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        //expects
        Assertions.assertThrows(UserNotFoundException.class, () -> getUserService.getUserById("NOT_EXISTING_PESEL"));
    }

    @Test
    void shouldSaveUser() {
        //given
        String pesel = "11111111111";
        userPort.saveUser(new User("Dominik", "Kaczka", pesel, List.of()));
        //when
        User userById = getUserService.getUserById(pesel);
        //then
        Assertions.assertNotNull(userById);
    }


}