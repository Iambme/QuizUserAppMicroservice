package com.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.entity.User;
import com.quiz.service.interf.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.quiz.prototype.UserPrototype.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserRestControllerTest {
    private final UserService userService = mock(UserService.class);
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    List<User> users;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        users.add(getUserWithTheSameEmail());
        users.add(getUserFullParameter());
        mockMvc = MockMvcBuilders.standaloneSetup(new UserRestController(userService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAll() throws Exception {
        when(userService.findAll()).thenReturn(users);
        mockMvc.perform(get("/user/all").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(users)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(users)));
        verify(userService, times(1)).findAll();
    }

    @Test
    void getUser() throws Exception {
        when(userService.findUserById(1)).thenReturn(getUserFullParameter());
        mockMvc.perform(get("/user/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getUserFullParameter())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(getUserFullParameter())));
    }

    @Test
    void updateUser() throws Exception {
        mockMvc.perform(put("/user/update")
                .param("user", objectMapper.writeValueAsString(getUserWithoutRoles()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getUserWithoutRoles())))
                .andExpect(status().isOk());
        verify(userService, times(1)).updateUser(getUserWithoutRoles());
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/user/{id}", 1))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteByID(1);
    }
}