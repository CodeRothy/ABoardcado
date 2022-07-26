package com.rim.aboardcado.dto;

import com.rim.aboardcado.domain.entity.Board;
import com.rim.aboardcado.domain.entity.Comment;
import com.rim.aboardcado.domain.entity.Member;
import lombok.*;

import java.time.LocalDateTime;


@Builder
@Getter
//@NoArgsConstructor
//@AllArgsConstructor
public class CommentDto {

    private Long commentId;

    private String comment;

    private String author;

    private LocalDateTime createdDate;


    // CommentDto -> Comment
    public Comment toEntity(String author, Board board, Member member){
        Comment build = Comment.builder()
                .comment(comment)
                .author(author)
                .createdDate(LocalDateTime.now())
                .board(board)
                .member(member)
                .build();
        return build;
    }
}
