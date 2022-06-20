package com.rim.aboardcado.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;


@Getter
@Builder
public class MemberDto {

    private final PasswordEncoder passwordEncoder;

    private String name;
    private String email;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}