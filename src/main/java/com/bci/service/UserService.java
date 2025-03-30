package com.bci.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bci.entity.Role;
import com.bci.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.stream.Collectors;
import com.bci.entity.User;


@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        
        log.info("username = {}", user.getUsername());
        log.info("password = {}", user.getPassword());
        
        for (Role rol : user.getRoles()) {
        	log.info("rol id = {}" , rol.getId());
			log.info("rol name = {}" , rol.getName());
			
		}
        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                    //.map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toSet())
        );
    }

    public User registerUser(String username, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        Role userRole = new Role();
        userRole.setName(role);

        user.setRoles(Set.of(userRole));
        return userRepository.save(user);
    }
}

