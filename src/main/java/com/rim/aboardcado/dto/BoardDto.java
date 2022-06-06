package com.rim.aboardcado.dto;

import com.rim.aboardcado.domain.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class BoardDto {
    private Long id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Board toEntity() {
        Board build = Board.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .build();
        return build;
    }
}