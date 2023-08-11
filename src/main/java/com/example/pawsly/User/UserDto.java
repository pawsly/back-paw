package com.example.pawsly.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String userid;
    private String password;

    @Builder
    public UserDto(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }

    public String getuserid() {
        return  userid;
    }

    public String getPassword() {
        return password;
    }



}
