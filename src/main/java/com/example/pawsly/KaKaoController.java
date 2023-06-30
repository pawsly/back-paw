package com.example.pawsly;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class KaKaoController {

    @Autowired
    KaKaoService userService;

    @ResponseBody
    @GetMapping("/app/users/kakao")
    public void  kakaoCallback(@RequestParam String code) throws Exception {

        System.out.println(code);
        String access_Token = userService.getKaKaoAccessToken(code);
        userService.createKakaoUser(access_Token);

    }



}
