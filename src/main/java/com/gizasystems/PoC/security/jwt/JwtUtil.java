package com.gizasystems.PoC.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "f113afc46c55d502fd3470f932a4486880a86a23ee137d8021ab5f003b3a41736f4c586c6751ef3f981de28968ddf7bcd2e403d5366c2749ee4c91801827b9dad76d90ad0e3b40fb703bed2d034b06671df8f3f6e8ae5f5b2d3e271a1df80e9604a54907c9aa73a92416840a351fa0bf1674f66922050e2b5a717a72831d49bd8571bcc6e6e715f8a6f4056d2df3ead8f3fa18b44ab2b5d174954ec0d3301f31b0d80bdb91b233ea7183de4aef93747a710c50366fb83ff8efcd0078eb8d08f681a6a09aba3319b8a22a7819ca026c5ceb31faecd4ace7e74452c265fe8d525e846cde4fc5e8375d55c2efde572847dd1c9de67d603845efb71a80a24ef4954e";
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, UserDetails user) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
}
