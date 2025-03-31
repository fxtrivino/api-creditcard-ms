package com.bci.controlller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.bci.entity.LoginDto;
import com.bci.entity.UserDto;
import com.bci.service.impl.UserServiceImpl;
import com.bci.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserServiceImpl userService;

    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return new ResponseEntity<>("Usuario registrado con éxito", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
        UserDetails userDetails = userService.loadUserByUsername(loginDto.getUsername());
        if (new BCryptPasswordEncoder().matches(loginDto.getPassword(), userDetails.getPassword())) {
            String token = JwtUtil.generateToken(loginDto.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>("Credenciales inválidas", HttpStatus.FORBIDDEN);
    }
}

