package com.example.pawsly.user1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class User {

    @Id
    @Column(unique = true, columnDefinition = "VARCHAR(20)")
    private String userid; //로그인 할 때 id
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private String phone;
    @Column(unique = true)
    private String nickname;
    private String provider; //sns로그인 시 provider를 통해 email이 겹치지 않는지 확인
    private String create_day;
    private String birth;
    private String edit_day;

    public User update(String email, String name) {
        this.name = name;
        this.email = email;
        return this;
    }


}
