package com.rim.aboardcado.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class BoardDto {

    private Long id;

    @NotEmpty(message = "작성자 입력은 필수입니다.")
    @Length(max = 10, message = "10자 이하만 가능합니다.")
    private String author;

    @NotEmpty(message = "제목 입력은 필수입니다.")
    @Length(max = 50, message = "50자 이하만 가능합니다.")
    private String title;

    @NotEmpty(message = "내용 입력은 필수입니다.")
    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}