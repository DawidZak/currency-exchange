package com.exchange.task.user.api;


import com.exchange.task.user.domain.GetUserService;
import com.exchange.task.user.domain.UserAlreadyExists;
import com.exchange.task.user.domain.UserNotFoundException;
import com.exchange.task.user.domain.WriteUserService;
import com.exchange.task.user.domain.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
public class UserController {
    //TODO swagger
    private final WriteUserService writeUserService;
    private final GetUserService getUserService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        try {
            return getUserService.getUserById(id);
        } catch (UserNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found ", exc);
        }
    }

    @PostMapping
    public User createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        try {
            return writeUserService.createUser(createUserRequest);
        } catch (UserAlreadyExists exc) {
            //TODO add cause
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User already exists ", exc);
        }
    }
}
