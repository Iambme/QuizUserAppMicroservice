package com.quiz.service.interf;

import com.quiz.entity.User;
import com.quiz.exception.UserNotFoundException;
import com.quiz.exception.ValidationException;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findUserByEmail(String email) throws UserNotFoundException;

    User findUserByEmailAndPassword(String email, String password) throws UserNotFoundException;

    User findUserById(Integer id) throws UserNotFoundException;

    void addUser(User user) throws ValidationException;

    void deleteByID(Integer id);

    void updateUser(User user);

    boolean isExist(String email);

}
