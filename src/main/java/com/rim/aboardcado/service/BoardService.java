package com.rim.aboardcado.service;

import com.rim.aboardcado.domain.entity.Board;
import com.rim.aboardcado.domain.repository.BoardRepository;
import com.rim.aboardcado.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class BoardService {

    private BoardRepository boardRepository;

    // 글 작성 저장
    @Transactional
    public Long savePost(BoardDto boardDto) {

        return boardRepository.save(boardDto.toEntity()).getId();
    }

    // 글 리스트 및 페이징
    @Transactional
    public List<BoardDto> getBoardList(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        List<BoardDto> boardList = new ArrayList<>();

        for (Board board : boards) {
            boardList.add(this.toEntity(board));
        }
        return boardList;
    }

    // 글 검색
    @Transactional
    public List<BoardDto> searchPosts(String keyword, Pageable pageable) {
        Page<Board> boards = boardRepository.findByTitleContaining(keyword, pageable);
        List<BoardDto> boardList = new ArrayList<>();

        if (boards.isEmpty()) return boardList;

        for (Board board : boards) {
            boardList.add(this.toEntity(board));
        }
        return boardList;
    }

    // 글 상세보기
    @Transactional
    public BoardDto postDtl(Long id) {
        Board board = boardRepository.findById(id).get();

        BoardDto boardDto = this.toEntity(board);
        return boardDto;
    }

    // 글 삭제
    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    // boardDto 빌더패턴
    @Transactional
    private BoardDto toEntity(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .author(board.getAuthor())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .build();
    }

}
