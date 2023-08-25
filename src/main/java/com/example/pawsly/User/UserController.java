package com.example.pawsly.User;

import com.example.pawsly.Jwt.JwtTokenProvider;
import com.example.pawsly.Jwt.Refresh.RefreshTokenService;
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
    private final RefreshTokenService refreshTokenService; // RefreshTokenService 추가

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider,
                          UserRepository userRepository,RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
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
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshTokens(@RequestBody Map<String, String> requestMap) {
        String refreshToken = requestMap.get("refreshToken");

        try {
            // 리프레시 토큰을 사용하여 새로운 액세스 토큰 발급
            String newAccessToken = refreshTokenService.refreshAccessToken(refreshToken);

            // 새로운 액세스 토큰을 응답으로 반환
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("accessToken", newAccessToken);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Failed to refresh tokens");
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

/*
    @GetMapping("/login/get-cookie")
    public String getCookieValue(@CookieValue(name = "user_key", defaultValue = "no-cookie") String userKey) {
        if (!userKey.equals("no-cookie")) {
            return "User Key from Cookie: " + userKey;
        } else {
            return "No user key cookie found.";
        }
    }*/

