package com.example.pawsly.OAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OAuthController {

    @Autowired
    KaKaoService userService;

    @Autowired
    NaverService naverService;

    @GetMapping("/user/kakao")
    public ResponseEntity<?> kakaoCallback(@RequestParam String code) throws Exception {
        System.out.println(code);
        String access_Token = userService.getKaKaoAccessToken(code);
        if (access_Token == null) {
            // access_Token이 null인 경우, 400 Bad Request와 에러 메시지를 클라이언트로 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kakao access token is null.");
        }
        userService.createKakaoUser(access_Token);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/user/naver")
    public ResponseEntity<?> naverCallback(@RequestParam String code) throws Exception {
        System.out.println(code);
        String access_Token = naverService.getNaverAccessToken(code);
        if (access_Token == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Naver access token is null.");
        }
        naverService.createNaverUser(access_Token);

        return ResponseEntity.ok().build();
    }

}

