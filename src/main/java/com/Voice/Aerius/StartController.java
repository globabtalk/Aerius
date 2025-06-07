package com.Voice.Aerius;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StartController {
    @GetMapping("/")
    public String start(){
        return "aerius starting";
    }

}
