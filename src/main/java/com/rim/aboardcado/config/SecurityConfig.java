package com.rim.aboardcado.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                    .authorizeRequests() // 요청에 대한 보안인증 체크 선언
                    .antMatchers("/resources/**","/error").permitAll()
                    .antMatchers("/","/join","/login","/search/**").permitAll()
                    .antMatchers("/post").hasRole("USER")
                    .anyRequest().authenticated() // 모든 요청에 체크 (권한 상관X)
            .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .usernameParameter("email")
                    .failureUrl("/login/error")
            .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
//            .and()
//                    .exceptionHandling()
//                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}