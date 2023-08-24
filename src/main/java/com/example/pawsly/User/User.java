package com.example.pawsly.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User implements UserDetails {

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
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한 정보를 SimpleGrantedAuthority 형식으로 반환
        return Arrays.stream("ROLE_USER".split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userKey;
    }


    @Override
    public boolean isAccountNonExpired() {
        // 계정 유효기간 만료 여부를 반환하는 로직을 구현
        return true; // 예시로 일단 true로 설정
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠김 여부를 반환하는 로직을 구현
        return true; // 예시로 일단 true로 설정
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 비밀번호 유효기간 만료 여부를 반환하는 로직을 구현
        return true; // 예시로 일단 true로 설정
    }

    @Override
    public boolean isEnabled() {
        // 계정 활성화 여부를 반환하는 로직을 구현
        return true; // 예시로 일단 true로 설정
    }

    public String getUserKey() {
        return userKey;
    }
}
