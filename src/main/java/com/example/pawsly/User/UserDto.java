package com.example.pawsly.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
