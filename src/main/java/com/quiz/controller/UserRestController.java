package com.quiz.controller;

import com.quiz.entity.User;
import com.quiz.exception.UserNotFoundException;
import com.quiz.service.interf.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserRestController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@Validated @RequestBody User user) {
        userService.updateUser(user);
        log.info("user successfully updated " + user.toString());
        return ResponseEntity.ok("user successfully updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userService.deleteByID(id);
        log.info("user successfully deleted with id : " + id);
        return ResponseEntity.ok("user successfully deleted");
    }
}
