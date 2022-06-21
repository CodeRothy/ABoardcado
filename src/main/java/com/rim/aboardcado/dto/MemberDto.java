package com.rim.aboardcado.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Getter
@Builder
public class MemberDto {

    private final PasswordEncoder passwordEncoder;

    @NotEmpty
    @Length (min = 2, max = 10, message = "2 ~ 10자 사이만 가능합니다.")
    private String name;

    @NotBlank
    @Email(message = "이메일 형식을 확인해주세요.")
    private String email;

    @NotBlank
    @Length(min = 6, max = 16, message = "6 ~ 16자 사이만 가능합니다")
    private String password;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}