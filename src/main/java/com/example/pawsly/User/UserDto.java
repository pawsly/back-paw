package com.example.pawsly.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String userid;
    private String password;
    private String userKey;
    private String nickname;
    private String name;
    @Builder
    public UserDto(String userid, String password,String userKey,String name,String nickname) {
        this.userid = userid;
        this.password = password;
        this.userKey=userKey;
        this.nickname=nickname;
        this.name=name;
    }

    public String getuserid() {
        return  userid;
    }
    public String getuserKey(){
        return userKey;
    }
    public String getPassword() {
        return password;
    }



}
