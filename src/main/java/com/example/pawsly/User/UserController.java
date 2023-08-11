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
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDto userDto) {
        try {
            boolean isAuthenticated = userService.login(userDto.getEmail(), userDto.getPassword());

            if (isAuthenticated) {
                // 사용자가 로그인에 성공했을 때, 로그인한 사용자 정보를 가져옵니다.
                User loggedInUser = userService.getUserByEmail(userDto.getEmail());
                System.out.println(2);
                // 프론트엔드로 응답할 사용자 정보를 담을 맵을 생성합니다.
                Map<String, String> response = new HashMap<>();
                response.put("userid", loggedInUser.getUserid());
                response.put("email", loggedInUser.getEmail());
                response.put("nickname", loggedInUser.getNickname());
                response.put("name", loggedInUser.getName());
                response.put("phone", loggedInUser.getPhone());
                response.put("birth", loggedInUser.getBirth());
                response.put("userKey", loggedInUser.getUserKey().toString());
                System.out.println("User login successfully");

                // 응답으로 맵을 보냅니다.
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
