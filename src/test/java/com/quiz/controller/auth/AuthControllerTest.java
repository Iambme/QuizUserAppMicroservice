package com.quiz.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.config.security.jwt.JwtProvider;
import com.quiz.entity.Role;
import com.quiz.service.interf.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.quiz.prototype.UserPrototype.getUserFullParameter;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthControllerTest {
    private final UserService userService = mock(UserService.class);
    private final JwtProvider jwtProvider = mock(JwtProvider.class);
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    private RegistrationRequest registrationRequest;
    private AuthRequest authRequest;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(userService, jwtProvider)).build();
        objectMapper = new ObjectMapper();
        registrationRequest = new RegistrationRequest("mashazz@mail.ru", "Aa1234567",
                "Masha", "Ivanova", Collections.singleton(new Role(3, "ROLE_STUDENT")));
        authRequest = new AuthRequest("mashazz@mail.ru", "Aa1234567");
    }

    @Test
    void registerUser() throws Exception {
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isOk());
        verify(userService, times(1)).addUser(getUserFullParameter());
    }

    @Test
    void auth() throws Exception {
        when(userService.findUserByEmailAndPassword(getUserFullParameter().getEmail(), getUserFullParameter().getPassword()))
                .thenReturn(getUserFullParameter());
        when(jwtProvider.generateToken(authRequest.getLogin())).thenReturn("testToken");

        String token = jwtProvider.generateToken(getUserFullParameter().getEmail());
        mockMvc.perform(post("/auth").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new AuthResponse(token))));
        verify(userService, times(1)).findUserByEmailAndPassword(authRequest.getLogin(), authRequest.getPassword());
        verify(userService, times(1)).findUserByEmailAndPassword(authRequest.getLogin(), authRequest.getPassword());
    }
}