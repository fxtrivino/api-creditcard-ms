package com.bci;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.bci.entity.CreditCardDto;
import com.bci.entity.Role;
import com.bci.entity.User;
import com.bci.filter.JwtAuthenticationFilter;
import com.bci.repository.CreditCardRepository;
import com.bci.repository.UserRepository;
import com.bci.service.impl.CreditCardServiceImpl;
import com.bci.service.impl.UserServiceImpl;
import com.bci.util.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class ApiCreditcardMsApplicationTest {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private FilterChain filterChain;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private CreditCardRepository creditCardRepository;

    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testNoAuthorizationHeaderSkipsFilter() throws ServletException, IOException {
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }
    

    @Test
    void testLoadUserByUsername_UserExists() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        user.setRoles(Set.of(new Role(1L, "ROLE_USER")));

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertFalse(userDetails.getAuthorities().isEmpty());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String username = "nonexistentuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }
    

    @Test
    void testStoreCard() {
        String cardNumber = "1234567812345678";
        
        CreditCardDto creditCardDto = new CreditCardDto();
        creditCardDto.setCardNumber(cardNumber);
        
        when(creditCardRepository.save(any())).thenReturn(null);
        
        assertDoesNotThrow(() -> creditCardService.storeCard(creditCardDto));
        verify(creditCardRepository, times(1)).save(any());
    }
    


}

