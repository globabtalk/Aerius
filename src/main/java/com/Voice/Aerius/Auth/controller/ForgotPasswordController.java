package com.Voice.Aerius.Auth.controller;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Voice.Aerius.Auth.service.EmailService;
import com.Voice.Aerius.Auth.repository.UserRepository;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ForgotPasswordController {

    private final EmailService service;
    private final UserRepository userRepository;
    private final static String RESPONSE_SUCCESS = "Password reset mail sent to your mail";
    private final static String RESPONSE_FAILURE = "Something went wrong";
    private final static String RESPONSE_TOO_MANY_REQUESTS = "Too many requests, try after 60 sec";
    public final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String userIdOrIp) {
        return buckets.computeIfAbsent(userIdOrIp, key -> 
            Bucket4j.builder().addLimit(Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1)))).build());
    
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<String> sendForgotPasswordMail(@RequestBody String email){
        Bucket bucket = resolveBucket(email);
        if(bucket.tryConsume(1))
        {
            try{
                if(userRepository.findByEmail(email) != null)
                    service.sendForgotPasswordMail(email);
                return ResponseEntity.ok(RESPONSE_SUCCESS);
            }
            catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RESPONSE_FAILURE);
            }       
        }
        else{
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(RESPONSE_TOO_MANY_REQUESTS);
        }     
    }
}
