package com.example.pawsly.OAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OAuthController {

    @Autowired
    KaKaoService userService;

    @GetMapping("/user/kakao")
    public ResponseEntity<?> kakaoCallback(@RequestParam String code) throws Exception {
        System.out.println(code);
        String access_Token = userService.getKaKaoAccessToken(code);
        userService.createKakaoUser(access_Token);

        return ResponseEntity.ok().build();
    }
/*
    @ResponseBody
    @PostMapping("user/naver")
    public ResponseEntity<?> naverCallback(@RequestParam String code) throws Exception {
        System.out.println(code);
        String access_Token = naverService.getNaverAccessToken(code);
        naverService.createNaverUser(access_Token);

        return ResponseEntity.ok().build();
    }*/

}

