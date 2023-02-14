package com.example.pawsly.user;

import lombok.*;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username; //아이디

    @Column(unique = true)
    private String username2; //닉네임

    private String password; //비밀번호

    @Column(unique = true)
    private String email; //이메일

    private String sex;
    private String phone_number;
    private String birth;
}