package com.quiz.service.impl;

import com.quiz.entity.Role;
import com.quiz.entity.User;
import com.quiz.exception.UserNotFoundException;
import com.quiz.exception.ValidationException;
import com.quiz.repository.RoleRepository;
import com.quiz.repository.UserRepository;
import com.quiz.service.interf.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.quiz.prototype.UserPrototype.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private UserService userService;


    List<User> users;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        users.add(getUserWithTheSameEmail());
        users.add(getUserFullParameter());
        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }


    @Test
    void findAll() {
        when(userRepository.findAll()).thenReturn(users);
        List<User> usersTest = userService.findAll();
        assertEquals(users, usersTest);
        verify(userRepository, times(1)).findAll();

    }

    @Test
    void addUser() throws ValidationException {
        when(passwordEncoder.encode(eq(getUserFullParameter().getPassword()))).thenReturn("testEncodePassword");
        when(roleRepository.findByName("ROLE_STUDENT")).thenReturn(Optional.of(new Role(3, "ROLE_STUDENT")));
        userService.addUser(getUserFullParameter());

        User u = getUserFullParameter();
        u.setPassword("testEncodePassword");
        verify(userRepository, times(1)).save(u);
        verify(userRepository, times(1)).findByEmail(getUserFullParameter().getEmail());
        verify(passwordEncoder, times(1)).encode("Aa1234567");

    }

    @Test
    void addUserWithValidationException() {
        when(userRepository.findByEmail(getUserWithTheSameEmail().getEmail())).thenReturn(Optional.of(getUserWithTheSameEmail()));
        assertThrows(ValidationException.class, () ->
                userService.addUser(getUserWithInvalidEmail()));
        assertThrows(ValidationException.class, () ->
                userService.addUser(getUserWithTheSameEmail()));
        assertThrows(ValidationException.class, () ->
                userService.addUser(getUserWithInvalidPassword()));
    }

    @Test
    void findUserByEmail() throws UserNotFoundException {
        when(userRepository.findByEmail(getUserFullParameter().getEmail())).thenReturn(Optional.of(getUserFullParameter()));
        User testUser = userService.findUserByEmail(getUserFullParameter().getEmail());
        assertEquals(testUser, getUserFullParameter());
        verify(userRepository, times(1)).findByEmail(getUserFullParameter().getEmail());
    }

    @Test
    void userNotFoundExceptionTest() {
        when(userRepository.findByEmail("testNonExistEmail")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findUserByEmail("testNonExistEmail"));
        verify(userRepository, times(1)).findByEmail("testNonExistEmail");

    }

    @Test
    void findUserByEmailAndPassword() throws UserNotFoundException {
        when(userRepository.findByEmail(eq(getUserFullParameter().getEmail())))
                .thenReturn(Optional.of(getUserFullParameter()));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        User testUser = userService.findUserByEmailAndPassword(getUserFullParameter().getEmail(), "Aa1234567");
        assertEquals(testUser, getUserFullParameter());
        verify(userRepository, times(1)).findByEmail(eq(getUserFullParameter().getEmail()));
        verify(passwordEncoder, times(1)).matches(getUserFullParameter().getPassword(), testUser.getPassword());
    }

    @Test
    void findUserById() throws UserNotFoundException {
        when(userRepository.findById(1)).thenReturn(Optional.of(getUserFullParameter()));
        User testUser = userService.findUserById(1);
        assertEquals(testUser, getUserFullParameter());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void deleteByID() {
        userService.deleteByID(getUserFullParameter().getId());
        verify(userRepository, times(1)).deleteById(getUserFullParameter().getId());
    }

    @Test
    void updateUser() {
        userService.updateUser(getUserFullParameter());
        verify(userRepository, times(1)).save(getUserFullParameter());
    }

    @Test
    void isExist() {
        userService.isExist(getUserFullParameter().getEmail());
        verify(userRepository, times(1)).findByEmail(getUserFullParameter().getEmail());
    }
}