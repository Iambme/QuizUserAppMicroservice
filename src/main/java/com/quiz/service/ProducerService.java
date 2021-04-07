package com.quiz.service;

import com.quiz.config.security.jwt.JwtProvider;
import com.quiz.exception.UserNotFoundException;
import com.quiz.service.interf.UserService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProducerService {
    private final KafkaTemplate<String, Integer> kafkaTemplate;
    private final JwtProvider jwtProvider;
    private final UserService userService;

//    public void produce(String message) {
//        System.out.println("Sending message : " + message);
//        kafkaTemplate.send("message", message);
//
//    }

    public void getUserInfoFromJwt(String token) throws UserNotFoundException {
        String login = jwtProvider.getLoginFromToken(token);
        kafkaTemplate.send("userInfo",userService.findUserByEmail(login).getId());

    }
}

