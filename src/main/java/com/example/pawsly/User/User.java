package com.example.pawsly.User;

import com.example.pawsly.OAuth.OAuthProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class User {

    @Id
    @Column(unique = true, columnDefinition = "VARCHAR(20)")
    private Long userid; //로그인 할 때 id
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private String phone;
    @Column(unique = true)
    private String nickname;
    private OAuthProvider oAuthProvider;
    private String create_day;
    private String birth;
    private String edit_day;

    @Builder
    public User(Long userid, String email,String password , String name, String phone ,String nickname,
                   OAuthProvider oAuthProvider,String birth){
        this.userid=userid;
        this.password=password;
        this.email=email;
        this.name= name;
        this.phone=phone;
        this.nickname=nickname;
        this.birth=birth;
        this.oAuthProvider = oAuthProvider;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
