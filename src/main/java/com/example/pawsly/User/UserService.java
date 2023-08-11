package com.example.pawsly.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

//연결
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 로직
    public void signUp(User user){
        if (userRepository.existsByUserid(user.getUserid())) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    // 로그인 로직
    public boolean login(String userid, String password) throws IllegalAccessException {
        Optional<User> userOptional = userRepository.findByUserid(userid); // 사용자 ID로 사용자 조회

        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return true; // 로그인 성공
        }
        return false;
    }

    // 사용자 아이디를 기반으로 사용자 정보를 조회하는 메서드
    public User getUserByUserid(String userid) {
        Optional<User> userOptional = userRepository.findByUserid(userid);
        return userOptional.orElse(null); // 사용자가 존재하지 않으면 null 반환
    }

}

