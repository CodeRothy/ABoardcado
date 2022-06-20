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

        return boardRepository.save(toEntity(boardDto)).getId();
    }

    // 글 리스트 및 페이징
    @Transactional
    public List<BoardDto> getBoardList(Pageable pageable) {
        Page<Board> boardList = boardRepository.findAll(pageable);
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (Board board : boardList) {
            boardDtoList.add(this.toDto(board));
        }
        return boardDtoList;
    }

    // 글 검색
    @Transactional
    public List<BoardDto> searchPosts(String title, String content, Pageable pageable) {
        Page<Board> boardList = boardRepository.findByTitleContainingOrContentContaining(title, content, pageable);
        List<BoardDto> boardDtoList = new ArrayList<>();

        if (boardDtoList.isEmpty()) return boardDtoList;

        for (Board board : boardList) {
            boardDtoList.add(this.toDto(board));
        }
        return boardDtoList;
    }

    // 글 상세보기
    @Transactional
    public BoardDto postDtl(Long id) {
        Board board = boardRepository.findById(id).get();

        BoardDto boardDto = this.toDto(board);
        return boardDto;
    }

    // 글 삭제
    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    // BoardDto -> Board
    public Board toEntity(BoardDto boardDto) {
        Board build = Board.builder()
                .id(boardDto.getId())
                .author(boardDto.getAuthor())
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .build();
        return build;
    }

    // Board -> boardDto 빌더패턴
    @Transactional
    public BoardDto toDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .author(board.getAuthor())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .build();
    }

}
