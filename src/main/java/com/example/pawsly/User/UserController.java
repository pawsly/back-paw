package com.example.pawsly.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final HttpSession httpSession;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, HttpSession httpSession,
                          UserRepository userRepository){
        this.userService = userService;
        this.httpSession = httpSession;
        this.userRepository=userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        try {
            userService.signUp(user);
            String responseMessage = "User signup successfully: " + user;
            System.out.println(responseMessage);
            ObjectMapper objectMapper = new ObjectMapper();
            String responseJson = objectMapper.writeValueAsString(user);
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Failed to signup: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Failed to serialize response: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user,HttpServletResponse response) {
        try {
            boolean isAuthenticated = userService.login(user.getUserid(), user.getPassword());

            if (isAuthenticated) {
                // 사용자가 로그인에 성공했을 때, 로그인한 사용자 정보를 가져옵니다.
                User loggedInUser = userService.getUserByUserid(user.getUserid());
                Cookie userCookie = new Cookie("user_key", loggedInUser.getUserKey().toString()); // 쿠키 이름을 "user_key"로 변경
                userCookie.setMaxAge(3600); // 쿠키 유효 시간 설정 (초 단위)
                userCookie.setMaxAge(3600); // 쿠키 유효 시간 설정 (초 단위)
                userCookie.setPath("/"); // 쿠키의 경로 설정
                System.out.println(userCookie);
                response.addCookie(userCookie);

                // 프론트엔드로 응답할 사용자 정보를 담을 맵을 생성합니다.
                Map<String, String>  responseBody = new HashMap<>();
                responseBody.put("userid", loggedInUser.getUserid());
                responseBody.put("email", loggedInUser.getEmail());
                responseBody.put("nickname", loggedInUser.getNickname());
                responseBody.put("name", loggedInUser.getName());
                responseBody.put("phone", loggedInUser.getPhone());
                responseBody.put("birth", loggedInUser.getBirth());
                responseBody.put("userKey", loggedInUser.getUserKey().toString());
                System.out.println("User login successfully");

                // 응답으로 맵을 보냅니다.
                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            } else {
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("message", "Login failed");
                return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
/*
    @GetMapping("/kakao/user")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{accessToken}")
    public ResponseEntity<User> findByAccessToken(@PathVariable String accessToken) {
        Long userId = authTokensGenerator.extractUserId(accessToken);
        return ResponseEntity.ok(userRepository.findByUserid(userId).get());
    }*/
}
