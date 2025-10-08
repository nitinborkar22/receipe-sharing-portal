package com.shakti.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String password;


    @Column(unique = true)
    private String email;

    @Column
    private String fullName;

}
