package com.example.pawsly.user1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final HttpSession httpSession;

    @Autowired
    public UserController(UserService userService, HttpSession httpSession){
        this.userService=userService;
        this.httpSession=httpSession;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User userDto) {
        try {
            userService.signUp(userDto);
            String responseMessage = "User signup successfully: " + userDto;
            System.out.println(responseMessage);
            ObjectMapper objectMapper = new ObjectMapper();
            String responseJson = objectMapper.writeValueAsString(userDto);
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Failed to signup: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Failed to serialize response: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        try {
            boolean isAuthenticated = userService.login(user.getEmail(), user.getPassword());

            if (isAuthenticated) {
                httpSession.setAttribute("user", user);
                System.out.println("User login successfully: " + user.getEmail());

                Map<String, String> response = new HashMap<>();
                response.put("message", "User login successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Login failed");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}