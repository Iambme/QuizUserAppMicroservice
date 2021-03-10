package com.quiz.controller.auth;

import com.quiz.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@ToString(exclude = "password")
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    private Set<Role> roles;
}