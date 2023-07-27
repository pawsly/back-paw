package com.example.pawsly.user1;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String userid;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String nickname;
    private String provider;
    @Builder
    public UserDto(String userid, String email,String password , String name, String phone ,String nickname,
                    String provider){
        this.userid=userid;
        this.password=password;
        this.email=email;
        this.name= name;
        this.phone=phone;
        this.nickname=nickname;
        this.provider=provider;
    }

    public User toEntity() {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPhone(phone);
        user.setNickname(nickname);
        user.setProvider(provider);


        return user;
    }

}