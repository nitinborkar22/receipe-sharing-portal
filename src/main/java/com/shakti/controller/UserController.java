package com.shakti.controller;


import com.shakti.model.User;
import com.shakti.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {


    @Autowired
    private UserRepository userRepository;


    @PostMapping("/users")
    public User createUser(@RequestBody User user) throws Exception {

        User isExits = userRepository.findByEmail(user.getEmail());
        if(isExits !=null){
            throw new Exception("User is already existed with email :" + user.getEmail() + ".Pls use another email id.");
        }
        User savedUser = userRepository.save(user);
        return savedUser;
    }


    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable Long userId) throws Exception {
        if(userRepository.existsById(userId)){
            userRepository.deleteById(userId);
            return "user deleted  successfully";
        } else {
            throw new Exception("User id is not available" + userId);
        }


    }
    
    
    @GetMapping("/users/")
    public List<User> getAllUsers(){
        List<User> allUsers = userRepository.findAll();
        return allUsers;
    }


//    public User findByEmail(String email) throws Exception {
//
//        return  user;
//    }
}
