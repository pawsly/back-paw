package com.example.pawsly;


import com.example.pawsly.Jwt.JwtAuthenticationFilter;
import com.example.pawsly.Jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user/login").permitAll() // 로그인 페이지는 인증 없이 접근 가능하도록 설정
                .antMatchers("/**").permitAll() // 모든 요청은 인증 없이 접근 가능하도록 설정 (필요에 따라 수정)
                .anyRequest().authenticated() // 나머지 요청은 인증된 사용자만 접근 가능
                .and()
                .formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/auth")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login")
                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // JwtAuthenticationFilter 추가


        return http.build();
    }

        @Bean
        PasswordEncoder passwordEncoder () {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            return new BCryptPasswordEncoder();
        }
    }

