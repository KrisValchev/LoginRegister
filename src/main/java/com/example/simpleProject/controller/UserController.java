package com.example.simpleProject.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.simpleProject.exception.UserAlreadyExistException;
import com.example.simpleProject.exception.UserDoesNotExists;
import com.example.simpleProject.model.User;
import com.example.simpleProject.response.JwtResponse;
import com.example.simpleProject.response.UserResponse;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import com.example.simpleProject.service.UserService;

import java.util.Date;
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

    public ResponseEntity<JwtResponse> loginUser(@RequestBody User userData) throws UserDoesNotExists {
        User user = userService.loginUser(userData);
        if (user == null) {
            throw new UserDoesNotExists("User does not exist");
        }
        // Generate JWT
        String token = generateJwtToken(user);

        // Return user and token
        UserResponse userResponse = new UserResponse(user.getId(),user.getEmail(),user.getFirstName(),user.getLastName(),user.getAddress());
        JwtResponse response = new JwtResponse(token,userResponse);
        return ResponseEntity.ok(response);
    }

    //private method generating jwt
private String generateJwtToken(User user){
     final String jwtSecret = "your-256-bit-secret-key-goes-here"; // Should be stored securely
     final long jwtExpirationInMs = 3600000; // 1 hour
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

    return Jwts.builder()
            .setSubject(String.valueOf(user.getId()))
            .claim("email", user.getEmail())
            // Add more claims as needed
            .setIssuedAt(now)
            .setExpiration(expiryDate)
           //Digitally signs the token to prevent tampering.
            .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
            .compact();
}
//    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get")
    public List<User> getUsers() {
        return userService.getUsers();
    }
    @GetMapping("get-user")
    public User getUser(@RequestBody User user) {
        User returnUser=userService.getUserById(user.getId());
        if(returnUser==null) {
            throw new UserDoesNotExists("User does not exist");
        }
        return returnUser;
    }


//method that only logged in users can access
    @GetMapping("/loggedUser")
    public ResponseEntity<UserResponse> getCurrentUser( Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Get user ID from the authentication principal (assumes it's set in the JWT filter)
        String userIdStr = authentication.getPrincipal().toString();
        Long userId = Long.parseLong(userIdStr);

        // Fetch user from DB
        // You need to inject UserService or UserRepository here
        User user = userService.getUserById(userId); // or userRepository.findById(userId).get()

        UserResponse response = new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getAddress()
        );

        return ResponseEntity.ok(response);
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
