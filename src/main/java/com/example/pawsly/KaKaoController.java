package com.example.pawsly;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
