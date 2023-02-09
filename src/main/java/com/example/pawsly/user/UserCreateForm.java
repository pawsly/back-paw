package com.example.pawsly.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
//회원가입 폼
public class UserCreateForm {
    @Size(min =3, max = 25)
    @NotEmpty(message = "ID를 꼭 입력해주세요.") //아이디
    private String username;

    @Size(min = 8,max = 12)
    @NotEmpty(message = "비밀번호를 꼭 입력해주세요.") //비밃번호
    private String password1;

    @Size(min = 8,max = 12)
    @NotEmpty(message = "비밀번호 확인을 꼭 입력해주세요.") //비밀번호 확인
    private String password2;

    @NotEmpty(message = "이메일을 꼭 입력해주세요.") //이메일 작성
    @Email
    private String email;

    @Size(min = 2,max = 10)
    @NotEmpty(message = "닉네임을 꼭 입력해주세요.") //닉네임 작성
    private String name;

    private String phone_number; //휴대폰 번호
    private String sex; //성별
    private String birth; //생년월일




}
