package com.rim.aboardcado.service;

import com.rim.aboardcado.domain.entity.Board;
import com.rim.aboardcado.domain.entity.Member;
import com.rim.aboardcado.domain.repository.BoardRepository;
import com.rim.aboardcado.domain.repository.CommentRepository;
import com.rim.aboardcado.domain.repository.MemberRepository;
import com.rim.aboardcado.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    // 댓글 등록
    @Transactional
    public void saveComment(CommentDto commentDto, String email, Long id) {

        Optional<Board> optBoard = boardRepository.findById(id);
        optBoard.ifPresent(selectBoard-> {
            Member member = memberRepository.findByEmail(email);
            String author = member.getName();

            commentRepository.save(commentDto.toEntity(author, selectBoard, member));
        });
    }

}
