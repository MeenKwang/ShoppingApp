package com.shopping.admin.controller;

import com.shopping.admin.dto.AuthRequest;
import com.shopping.admin.dto.AuthResponse;
import com.shopping.admin.security.CustomUserDetails;
import com.shopping.admin.security.jwt.JwtTokenUtil;
import com.shopping.common.entity.User;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthRestController {
    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthRestController(AuthenticationManager authManager, JwtTokenUtil jwtTokenUtil) {
        this.authManager = authManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        System.out.println(request.getEmail());
        System.out.println(request.getPassword());
        try {
            Authentication authentication = authManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            String accessToken = jwtTokenUtil.generateAccessToken(user);
            AuthResponse authResponse = new AuthResponse(user.getUsername(), accessToken);

            return ResponseEntity.ok(authResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
