package com.rim.aboardcado.service;

import com.rim.aboardcado.domain.entity.Board;
import com.rim.aboardcado.domain.entity.Member;
import com.rim.aboardcado.domain.repository.BoardRepository;
import com.rim.aboardcado.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class BoardService {

    private BoardRepository boardRepository;

    // 글 작성 저장
    @Transactional
    public Long savePost(BoardDto boardDto, String author) {

        return boardRepository.save(boardDto.toEntity(author)).getId();
    }

    // 리스트 및 페이징
    public List<BoardDto> getBoardList(Pageable pageable){
        Page<Board> boards = boardRepository.findAll(pageable);
        List<BoardDto> boardList = new ArrayList<>();

        for (Board board : boards){
            boardList.add(this.toDto(board));
        }
        return boardList;
    }

    // 검색 페이징
    public List<BoardDto> searchPosts(String title, String content, Pageable pageable){
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(title, content, pageable);
        List<BoardDto> boardList = new ArrayList<>();

        if(boards.isEmpty()) return boardList;

        for(Board board : boards){
            boardList.add(this.toDto(board));
        }

        return boardList;
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
                .member_id(board.getMember_id())
                .build();
    }

}
