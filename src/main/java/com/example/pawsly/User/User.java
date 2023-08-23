package com.example.pawsly.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(unique = true, columnDefinition = "VARCHAR(36)")
    private String userKey; // 고유한 UUID
    @Column(unique = true, columnDefinition = "VARCHAR(20)")
    private String userid;
    @Column(unique = true)
    private String email; //로그인 할 때 사용
    private String password;
    private String name;
    private String phone;
    @Column(unique = true)
    private String nickname;
    @CreatedDate // 생성일을 자동으로 업데이트하기 위한 설정// 생성일은 업데이트되지 않도록 설정
    private LocalDateTime createDay;
    private String birth;
    private String editDay;
    private String userIntro;

    @Builder
    public User(String userid, String email,String password , String name, String phone ,String nickname
                   ,String birth,LocalDateTime createDay){
        this.userKey = UUID.randomUUID().toString();
        this.userid=userid;
        this.password=password;
        this.email=email;
        this.name= name;
        this.phone=phone;
        this.nickname=nickname;
        this.birth=birth;
        this.createDay=createDay;
    }

    public Object getUserKey() {
        return userKey;
    }
}
