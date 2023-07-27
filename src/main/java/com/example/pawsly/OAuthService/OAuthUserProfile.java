package com.example.pawsly.OAuthService;

import com.example.pawsly.user1.User;
import com.example.pawsly.user1.UserDto;

import java.util.Optional;

public class OAuthUserProfile {
    //OAuth 사용자 프로필 정보를 저장하고 UserDto로 변환
    private String name;
    private String email;
    private String provider;
    private String nickname;

    public UserDto toUserDto() {
        return UserDto.builder()
                .name(name)
                .email(email)
                .provider(provider)
                .build();
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setUser_name(String name) {
        this.name = name;
    }

    public void setUser_email(String email) {
        this.email = email;
    }

    public String getUser_name() {
        return name;
    }

    public String getUser_email() {
        return email;
    }

}
