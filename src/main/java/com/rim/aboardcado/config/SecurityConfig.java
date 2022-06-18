package com.rim.aboardcado.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebSecurity
public class SecurityConfig  {
//
//    @Bean
//    public void configure(WebSecurity web) { // 4
//        web.ignoring().antMatchers("/css/**", "/js/**", "/image/**");
//    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 요청에 대한 보안인증 체크 선언
                .antMatchers("/css/**", "/js/**", "/image/**","/","/signup","**/login").permitAll()
                .antMatchers("/post").hasRole("USER")
                .anyRequest().authenticated() // 모든 요청에 체크
        .and()
                .formLogin() // 보안 인증은 formLogin 방식
                    .loginPage("/")
                    .defaultSuccessUrl("/")
                    .usernameParameter("email")
        .and()
                .logout()
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
