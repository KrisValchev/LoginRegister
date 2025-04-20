package com.example.simpleProject.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.simpleProject.exception.UserAlreadyExistException;
import com.example.simpleProject.exception.UserDoesNotExists;
import com.example.simpleProject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.simpleProject.service.UserService;

import java.util.List;

//Controller class serves to consume end-points from the Users resource  it has different RESTful methods
@CrossOrigin
//crossOrigin serves to provide client's communication with the server
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws UserAlreadyExistException {
        User newUser = userService.registerUser(user);
        if (newUser == null) {
       throw new UserAlreadyExistException("User with the same username or email already exists");
        }
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User userData) throws UserDoesNotExists {
        User user = userService.loginUser(userData);
        if (user == null) {
            throw new UserDoesNotExists("User does not exist");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/get")
    public List<User> getUser() {
        return userService.getUsers();
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserDoesNotExists.class)
    public ResponseEntity<String> handleUserDoesNotExists(UserDoesNotExists ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
