package com.shakti.controller;

import com.shakti.config.JwtProvider;
import com.shakti.model.User;
import com.shakti.repository.UserRepository;
import com.shakti.request.LoginRequest;
import com.shakti.response.AuthResponse;
import com.shakti.service.CustomUserDetailService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ------------------ SIGN UP ------------------
    @PostMapping("/signup")
    public AuthResponse createUser(@RequestBody User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new Exception("Email is already used with another account");
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        // Authenticate after signup
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        return AuthResponse.builder()
                .jwt(token)
                .message("Signup Success")
                .build();
    }

    // ------------------ SIGN IN ------------------
    @PostMapping("/signin")
    public AuthResponse signInHandler(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        return AuthResponse.builder()
                .jwt(token)
                .message("Signin Success")
                .build();
    }

    // ------------------ AUTHENTICATE ------------------
    private Authentication authenticate(String username, String rawPassword) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("User not found");
        }

        if (!passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
