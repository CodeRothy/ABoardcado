package com.rim.aboardcado.dto;

import com.rim.aboardcado.domain.entity.Board;
import com.rim.aboardcado.domain.entity.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.ForeignKey;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Setter
@Builder
@Getter
@ToString
public class BoardDto {

    private Long id;

//    @NotEmpty(message = "작성자 입력은 필수입니다.")
//    @Length(max = 10, message = "10자 이하만 가능합니다.")
//    private String author;

    @NotEmpty(message = "제목 입력은 필수입니다.")
    @Length(max = 20, message = "20자 이하만 가능합니다.")
    private String title;

    @NotEmpty(message = "내용 입력은 필수입니다.")
    private String content;

    private String author;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    private Member member;

    // BoardDto -> Board
    public Board toEntity(String author) {
        Board build = Board.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .member(member)
                .build();
        return build;
    }

}