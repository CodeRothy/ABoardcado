package com.rim.aboardcado.domain.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column
    private String comment;

    @Column
    private String author;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    // 원 게시글
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    // 유저 정보
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;




}
