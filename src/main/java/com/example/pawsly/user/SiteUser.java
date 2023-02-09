package com.example.pawsly.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class SiteUser {
//회원가입 할 때 사용할 데이터 테이블
    @Id //데이터베이스 테이블의 기본키와 객체의 필드 매핑
    @GeneratedValue(strategy = GenerationType.IDENTITY)//기본키 생성 어노테이션
    private long id; //기본키 생성

    @Column(unique=true) //중복확인
    private String username; //닉네임

    @Column(unique=true)
    private String email; //이메일

    private String password; //비밃번호
    private String name; //유저 성함
}
