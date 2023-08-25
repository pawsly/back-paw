package com.example.pawsly.Jwt.Refresh;

import com.example.pawsly.Jwt.JwtTokenProvider;
import com.example.pawsly.Jwt.TokenInfo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RefreshTokenService {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public RefreshTokenService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String refreshAccessToken(String refreshToken) {
        Claims refreshTokenClaims = jwtTokenProvider.parseClaims(refreshToken);

        if (refreshTokenClaims == null) {
            throw new RuntimeException("Refresh token claims could not be parsed.");
        }

        String userKey = refreshTokenClaims.getSubject();
        UserDetails userDetails = new User(userKey, "", new ArrayList<>());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());
        TokenInfo tokens = jwtTokenProvider.generateToken(authentication); // 액세스 토큰 발급
        return tokens.getAccessToken();
    }
}

