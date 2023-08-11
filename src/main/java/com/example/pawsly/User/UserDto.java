package com.example.pawsly.User;

import lombok.Builder;


public class UserDto {
    private String email;
    private String password;

    @Builder
    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return  email;
    }

    public String getPassword() {
        return password;
    }
}
