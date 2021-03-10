package com.quiz.service.interf;

import com.quiz.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();

    void addRole(Role role);

    Optional<Role> findByName(String name);
}
