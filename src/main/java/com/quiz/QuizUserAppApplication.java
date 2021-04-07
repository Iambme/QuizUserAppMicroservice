package com.quiz;

import com.quiz.exception.ValidationException;
import com.quiz.service.interf.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@AllArgsConstructor
public class QuizUserAppApplication {
    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(QuizUserAppApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testApplication() throws ValidationException {
//        userService.addUser(new User("iambme@mail.rus", "aA1234567", "a", "x", Collections.singleton(new Role("ROLE_ADMIN"))));
    }

}
