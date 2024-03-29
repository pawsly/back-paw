package com.example.pawsly.User;

import com.example.pawsly.Jwt.JwtTokenProvider;
import com.example.pawsly.Jwt.TokenInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    //커밋 테스트
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
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
    public ResponseEntity<Map<String, String>> login(@RequestBody User user, HttpServletResponse response) {
        try {
            boolean isAuthenticated = userService.login(user.getUserid(), user.getPassword());

            if (isAuthenticated) {
                // 사용자가 로그인에 성공했을 때, 로그인한 사용자 정보를 가져옵니다.
                User loggedInUser = userService.getUserByUserid(user.getUserid());
                Authentication authentication = new UsernamePasswordAuthenticationToken(loggedInUser, null, new ArrayList<>());
                TokenInfo tokens = jwtTokenProvider.generateToken(authentication);
                Claims claims = jwtTokenProvider.parseClaims(tokens.getAccessToken());

                // 프론트엔드로 응답할 사용자 정보를 담을 맵을 생성합니다.
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("userid", loggedInUser.getUserid());
                responseBody.put("email", loggedInUser.getEmail());
                responseBody.put("nickname", loggedInUser.getNickname());
                responseBody.put("name", loggedInUser.getName());
                responseBody.put("phone", loggedInUser.getPhone());
                responseBody.put("birth", loggedInUser.getBirth());
                responseBody.put("userKey", loggedInUser.getUserKey());
                responseBody.put("accessToken", tokens.getAccessToken());
                responseBody.put("refreshToken", tokens.getRefreshToken());
                System.out.println("User login Successfully");

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

}

