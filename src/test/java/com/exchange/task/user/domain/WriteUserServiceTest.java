package com.exchange.task.user.domain;

import com.exchange.task.user.api.CreateUserRequest;
import com.exchange.task.user.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

class WriteUserServiceTest {

    private final UserPort userPort = new UserPortImpl();
    private final WriteUserService writeUserService = new WriteUserService(userPort);

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        //given
        CreateUserRequest createUserRequest = new CreateUserRequest("D", "A", "111111111111", BigDecimal.ZERO);
        writeUserService.createUser(createUserRequest);
        //expects
        Assertions.assertThrows(UserAlreadyExists.class, () -> {
            writeUserService.createUser(createUserRequest);
        });
    }


    @Test
    void shouldSaveUser() {
        //given
        String pesel = "111111111111";
        String firstName = "Domniki";
        String lastName = "A";
        CreateUserRequest createUserRequest = new CreateUserRequest(firstName, lastName, pesel, BigDecimal.ZERO);
        //when
        writeUserService.createUser(createUserRequest);
        //then
        User user = userPort.findById(pesel).get();
        Assertions.assertEquals(user.getPesel(), pesel);
        Assertions.assertEquals(user.getFirstName(), firstName);
        Assertions.assertEquals(user.getLastName(), lastName);
        Assertions.assertEquals(2, user.getAccounts().size());
        //TODO
    }

    @Test
    void shouldUpdateUser() {
        //given
        UserPort userPortMock = Mockito.mock(UserPort.class);
        User user = new User("Dominik", "Kaczka", "111111111111", List.of());
        //when
        new WriteUserService(userPortMock).updateUser(user);
        //then
        Mockito.verify(userPortMock).saveUser(user);
    }

}