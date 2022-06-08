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

    // 글 리스트
    @Transactional
    public Page<Board> getBoardList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.Direction.DESC,"id");

        return boardRepository.findAll(pageable);
    }

//    @Transactional
//    public List<BoardDto> getBoardList() {
//        List<Board> boardList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id", "createdDate"));
//        List<BoardDto> boardDtoList = new ArrayList<>();
//
//        for (Board board : boardList) {
//            BoardDto boardDto = BoardDto.builder()
//                    .id(board.getId())
//                    .author(board.getAuthor())
//                    .title(board.getTitle())
//                    .content(board.getContent())
//                    .createdDate(board.getCreatedDate())
//                    .build();
//            boardDtoList.add(boardDto);
//        }
//        return boardDtoList;
//    }

    // 글 상세보기
    @Transactional
    public BoardDto postDtl(Long id) {
        Board board = boardRepository.findById(id).get();

        BoardDto boardDto = BoardDto.builder()
                .id(board.getId())
                .author(board.getAuthor())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .build();
        return boardDto;
    }

    // 글 삭제
    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

//    // 페이징
//    @Transactional(readOnly = true)


}
