package com.shakti.config;


import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtProvider {


    private SecretKey key =Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());


    public String generateToken(Authentication auth){

        String jwt = Jwts.builder()
                .claim("email", "auth.getName()") // Custom claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .signWith(key)
                .compact();

        return  jwt;

    }

    public String getEmailFromJwtToken(String jwt){
        jwt = jwt.substring(7); //Bearer jwt
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String email = String.valueOf(claims.get("email"));


        return email;
    }
}
