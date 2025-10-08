package com.shakti.controller;

import com.shakti.model.Receipe;
import com.shakti.model.User;
import com.shakti.service.ReceipeService;
import com.shakti.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReceipeController {
    @Autowired
    private ReceipeService receipeService;
    @Autowired
    private UserService userService;

    @PostMapping("/api/receipe/user/{userId}")
    public Receipe createReceipe(@RequestBody Receipe receipe, @PathVariable Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Receipe createdReceipe = receipeService.createReceipe(receipe, user);
        return createdReceipe;
    }

    @PutMapping("/api/receipe/{id}")
    public Receipe updateReceipe(@RequestBody Receipe receipe, @PathVariable Long id) throws Exception {
        Receipe updatedReceipe = receipeService.updateReceipe(receipe, id);
        return updatedReceipe;
    }

    @PutMapping("/api/receipe/{receipeId}/user/{userId}/like")
    public Receipe likeReceipe(@PathVariable Long receipeId, @PathVariable Long userId) throws Exception {
        User user = userService.findUserById(userId);
        return receipeService.likeReceipe(receipeId, user);
    }

    @GetMapping("/api/receipe/")
    public List<Receipe> getAllReceipe() {
        List<Receipe> allReceipe = receipeService.findAllReceipe();
        return allReceipe;
    }

    @DeleteMapping("/api/receipe/{recipeId}")
    public String deleteReceipeById(@PathVariable Long reciepeId) throws Exception {
        receipeService.findReceipeById(reciepeId);
        receipeService.deleteReceipe(reciepeId);
        return "Receipe deleted successfully";
    }
}