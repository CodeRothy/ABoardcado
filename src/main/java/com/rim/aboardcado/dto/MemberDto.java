package com.rim.aboardcado.dto;

import com.rim.aboardcado.domain.entity.Board;
import com.rim.aboardcado.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Getter
@Builder
public class MemberDto {

    private final PasswordEncoder passwordEncoder;

    private String name;
    private String email;
    private String password;

    public Member toEntityEncode() {
        Member build = Member.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();
        return build;
    }
}
