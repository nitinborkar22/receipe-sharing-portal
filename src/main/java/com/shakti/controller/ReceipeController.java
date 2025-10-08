package com.shakti.controller;

import com.shakti.model.Receipe;
import com.shakti.model.User;
import com.shakti.service.ReceipeService;
import com.shakti.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receipe")
public class ReceipeController {
    @Autowired
    private ReceipeService receipeService;
    @Autowired
    private UserService userService;

    @PostMapping("/user/{userId}")
    public Receipe createReceipe(@RequestBody Receipe receipe, @PathVariable Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Receipe createdReceipe = receipeService.createReceipe(receipe, user);
        return createdReceipe;
    }

    @PutMapping("/{id}")
    public Receipe updateReceipe(@RequestBody Receipe receipe, @PathVariable Long id) throws Exception {
        Receipe updatedReceipe = receipeService.updateReceipe(receipe, id);
        return updatedReceipe;
    }

    @PutMapping("/{receipeId}/like/user/{userId}")
    public Receipe likeReceipe(@PathVariable Long receipeId, @PathVariable Long userId) throws Exception {
        User user = userService.findUserById(userId);
        return receipeService.likeReceipe(receipeId, user);
    }

    @GetMapping("/")
    public List<Receipe> getAllReceipe() {
        List<Receipe> allReceipe = receipeService.findAllReceipe();
        return allReceipe;
    }

    @DeleteMapping("/{receipeId}")
    public String deleteReceipeById(@PathVariable Long receipeId) throws Exception {
        receipeService.findReceipeById(receipeId);
        receipeService.deleteReceipe(receipeId);
        return "Receipe deleted successfully" + receipeId;
    }
}