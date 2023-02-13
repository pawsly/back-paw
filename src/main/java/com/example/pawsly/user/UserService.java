package com.example.pawsly.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserEntity create(String username, String email, String password ,String username2//여기에 회원가입 정보 다른거 넣고 싶은데..
                          ){
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setUsername2(username2);
        user.setEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //security 클래스로 암호화하여 비밀번호 저장
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;

    }
}
