package com.rim.aboardcado.service;

import com.rim.aboardcado.domain.entity.Board;
import com.rim.aboardcado.domain.repository.BoardRepository;
import com.rim.aboardcado.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Long savePost(BoardDto boardDto) {

        return boardRepository.save(boardDto.toEntity()).getId();
    }

    // 글 리스트 및 페이징
    @Transactional
    public Page<Board> getBoardList(Pageable pageable) {
        Page<Board> boardList = boardRepository.findAll(pageable);

        return boardList;
    }

//    // 글 리스트 paging 3 성공분
//    @Transactional
//    public List<BoardDto> getBoardList(Integer pageNum) {
//        Page<Board> page = boardRepository
//                .findAll(PageRequest
//                        .of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "id")));
//
//        List<Board> boardList = page.getContent();
//        List<BoardDto> boardDtoList = new ArrayList<>();
//
//        for (Board board : boardList) {
//            boardDtoList.add(this.toEntity(board));
//        }
//        return boardDtoList;
//    }
//
    // boardDto 엔티티빌드 메소드
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

}
