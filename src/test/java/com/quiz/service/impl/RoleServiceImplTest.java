package com.quiz.service.impl;

import com.quiz.entity.Role;
import com.quiz.repository.RoleRepository;
import com.quiz.service.interf.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.quiz.prototype.RolePrototype.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {
    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final RoleService roleService = new RoleServiceImpl(roleRepository);

    List<Role> roles;

    @BeforeEach
    void setUp() {
        roles = new ArrayList<>();
        roles.add(getAdminRole());
        roles.add(getTutorRole());
        roles.add(getStudentRole());
    }

    @Test
    void findAll() {
        when(roleRepository.findAll()).thenReturn(roles);
        List<Role> rolesTest = roleService.findAll();
        Assertions.assertEquals(rolesTest, roles);
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void addRole() {
        roleService.addRole(getAdminRole());
        verify(roleRepository, times(1)).save(getAdminRole());

    }

    @Test
    void findByName() {
        when(roleRepository.findByName(getStudentRole().getName())).thenReturn(Optional.of(getStudentRole()));
        Assertions.assertEquals(Optional.of(getStudentRole()),roleService.findByName(getStudentRole().getName()));
        verify(roleRepository, times(1)).findByName(getStudentRole().getName());
    }
}