package com.shakti.repository;

import com.shakti.model.Receipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceipeRepository extends JpaRepository<Receipe, Long> {

    // Example: Find all recipes by a specific user
    List<Receipe> findByUserId(Long userId);

    // Example: Search recipes by title (case-insensitive)
    List<Receipe> findByTitleContainingIgnoreCase(String title);
}
