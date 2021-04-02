package com.quiz.prototype;

import com.quiz.entity.Role;
import com.quiz.entity.User;
import lombok.experimental.UtilityClass;

import java.util.Collections;

@UtilityClass
public class UserPrototype {

    public static User getUserWithInvalidPassword() {
        User userWithInvalidPassword = new User();
        userWithInvalidPassword.setId(2);
        userWithInvalidPassword.setEmail("pochta@google.com");
        userWithInvalidPassword.setPassword("1234");
        return userWithInvalidPassword;

    }

    public static User getUserFullParameter() {
        return new User("mashazz@mail.ru", "Aa1234567",
                "Masha", "Ivanova", Collections.singleton(new Role(3, "ROLE_STUDENT")));
    }
    public static User getUserWithoutRoles() {
        return new User("mashazz@mail.ru", "Aa1234567",
                "Masha", "Ivanova");
    }

    public static User getUserWithInvalidEmail() {
        User userWithInvalidEmail = new User();
        userWithInvalidEmail.setEmail("куцук!!!!!!5ваваы@fds.eew");
        return userWithInvalidEmail;
    }

    public static User getUserWithTheSameEmail() {
        return new User("misha@mail.ru", "324AaAlolo",
                "Misha", "Ivanov", Collections.singleton(new Role(2, "ROLE_TUTOR")));
    }
}
