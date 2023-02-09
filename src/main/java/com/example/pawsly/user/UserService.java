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
    public SiteUser create(String username,String email,String password //여기에 회원가입 정보 다른거 넣고 싶은데..
                          ){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //security 클래스로 암호화하여 비밀번호 저장
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;

    }
}
