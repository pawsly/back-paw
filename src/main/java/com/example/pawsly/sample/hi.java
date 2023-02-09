package com.example.pawsly.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hi {

    @GetMapping("/hello")
    public String hi(){
        return  "Hello";
    }

}
