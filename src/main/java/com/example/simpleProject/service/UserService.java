package com.example.simpleProject.service;
import com.example.simpleProject.model.User;

import org.springframework.stereotype.Service;

import com.example.simpleProject.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){

        this.userRepository=userRepository;
    }
    public User registerUser(User user){
        if(userRepository.existsByEmail(user.getEmail())==true){
         return null;
        }
       return this.userRepository.save(user);
    }

    public User loginUser(User userData){
        //make validations in login and register components for empty fields so the user details won't be null
        User user=this.userRepository.findByEmail(userData.getEmail());
        if(user.getPassword().equals((userData.getPassword()))){
        return user;
        }else{
            return null;
        }
    }
    public List<User> getUsers(){
        return this.userRepository.findAll();
    }

}
