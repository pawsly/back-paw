package com.example.pawsly.user1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User userDto) {
        try {
            userService.signUp(userDto);
            String responseMessage = "User signup successfully: " + userDto; // 입력 받은 이메일 값을 사용하여 응답 메시지 생성
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>("Failed to signup: "+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) throws IllegalAccessException {
        boolean isAuthenticated = userService.login(user.getUserid(), user.getPassword());

        if (isAuthenticated) {
            System.out.println("User login successfully: " + user.getUserid());
            return new ResponseEntity<>("User login successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Login failed", HttpStatus.UNAUTHORIZED);
        }
    }

}