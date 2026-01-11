package com.example.mPack.controller;

import com.example.mPack.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        // Authenticate username & password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Generate tokens
        String accessToken = jwtUtil.generateToken(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);

        return response;
    }

    @PostMapping("/refresh")
    public Map<String, String> refreshToken(@RequestBody Map<String, String> request) {

        String refreshToken = request.get("refreshToken");
        String username = jwtUtil.extractUsername(refreshToken);

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Validate refresh token
        if (jwtUtil.validateToken(refreshToken, userDetails)) {
            String newAccessToken = jwtUtil.generateToken(username);
            return Map.of("accessToken", newAccessToken);
        } else {
            throw new RuntimeException("Invalid Refresh Token");
        }
    }
}
