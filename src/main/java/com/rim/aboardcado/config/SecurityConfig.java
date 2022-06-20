package com.rim.aboardcado.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig  {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 요청에 대한 보안인증 체크 선언
                .antMatchers("/css/**", "/js/**", "/image/**").permitAll()
                .antMatchers("/","/join").permitAll()
                .antMatchers("/post").hasRole("USER")
                .anyRequest().authenticated() // 모든 요청에 체크 (권한 상관X)
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}