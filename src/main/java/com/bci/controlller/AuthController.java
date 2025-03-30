package com.bci.controlller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.bci.service.UserService;
import com.bci.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        userService.registerUser(username, password, role.toUpperCase());
        return "Usuario registrado con éxito";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        UserDetails userDetails = userService.loadUserByUsername(username);
        if (new BCryptPasswordEncoder().matches(password, userDetails.getPassword())) {
            return JwtUtil.generateToken(username);
        }
        return "Credenciales inválidas";
    }
}

