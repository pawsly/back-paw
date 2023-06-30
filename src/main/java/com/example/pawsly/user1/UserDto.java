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

    @Builder
    public UserDto(String userid, String email,String password , String name, String phone ,String nickname){
        this.userid=userid;
        this.password=password;
        this.email=email;
        this.name=name;
        this.phone=phone;
        this.nickname=nickname;
    }

}