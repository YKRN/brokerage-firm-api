package com.brokerage.util;

import com.brokerage.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String secretKey = "mySecretKey";
    private String token;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil(secretKey);
        token = Jwts.builder()
                .setSubject("testuser")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Test
    public void testGenerateToken() {
        String generatedToken = jwtUtil.generateToken("testuser");
        assertNotNull(generatedToken);
    }

    @Test
    public void testValidateToken() {
        assertTrue(jwtUtil.validateToken(token, "testuser"));
    }

    @Test
    public void testExtractUsername() {
        String username = jwtUtil.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    public void testExtractExpiration() {
        Date expiration = jwtUtil.extractExpiration(token);
        assertNotNull(expiration);
    }
}