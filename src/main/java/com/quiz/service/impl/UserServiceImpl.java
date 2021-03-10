package com.quiz.service.impl;

import com.quiz.entity.Role;
import com.quiz.entity.User;
import com.quiz.exception.UserNotFoundException;
import com.quiz.exception.ValidationException;
import com.quiz.repository.RoleRepository;
import com.quiz.repository.UserRepository;
import com.quiz.service.interf.UserService;
import com.quiz.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(@NotNull User user) throws ValidationException {
        if (!ValidationUtils.isValidEmail(user.getEmail())) {
            throw new ValidationException("invalid email");
        }
        if (isExist(user.getEmail())) {
            throw new ValidationException("email already exists");
        }
        if (!ValidationUtils.isValidPassword(user.getPassword())) {
            throw new ValidationException("invalid password");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.setRoles(persistRoles(user.getRoles()));
        } else {
            Set<Role> defaultRole = new HashSet<>();
            defaultRole.add(new Role("ROLE_STUDENT"));
            user.setRoles(persistRoles(defaultRole));
        }

        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) throws UserNotFoundException {
        User user = findUserByEmail(email);
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }

        throw new UserNotFoundException();
    }

    @Override
    public User findUserById(Integer id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void deleteByID(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean isExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    private Set<Role> persistRoles(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .map(roleRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
