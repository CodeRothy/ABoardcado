package com.rim.aboardcado.service;

import com.rim.aboardcado.domain.entity.Board;
import com.rim.aboardcado.domain.entity.Member;
import com.rim.aboardcado.domain.repository.BoardRepository;
import com.rim.aboardcado.domain.repository.MemberRepository;
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
    private MemberRepository memberRepository;

    // 글 작성 저장
    @Transactional
    public Long savePost(BoardDto boardDto, String email) {

        // 현재 로그인 사용자 email 로 찾아 Member 불러오기
        Member member = memberRepository.findByEmail(email);

        // 작성자 이름 고정
        String author = member.getName();

        return boardRepository.save(boardDto.toEntity(author, member)).getId();
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

    // 로그인 회원과 board 작성자 동일 체크
    public boolean idCheck(BoardDto boardDto, String email) {

        Long authorId = boardRepository.findById(boardDto.getId()).get().getMember().getId();
        Long memberId = memberRepository.findByEmail(email).getId();

        if (authorId != memberId) {
            return false;
        }
        return true;
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
