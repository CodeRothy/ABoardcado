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
    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 페이지 블럭 수
    private static final int PAGE_POST_COUNT = 5; // 페이지 당 게시물 수

    // 글 작성 저장
    @Transactional
    public Long savePost(BoardDto boardDto) {

        return boardRepository.save(boardDto.toEntity()).getId();
    }

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
//    private BoardDto toEntity(Board board) {
//        return BoardDto.builder()
//                    .id(board.getId())
//                    .author(board.getAuthor())
//                    .title(board.getTitle())
//                    .content(board.getContent())
//                    .createdDate(board.getCreatedDate())
//                    .build();
//
//    }
//
//    @Transactional
//    public Long getBoardCount() {
//        return boardRepository.count();
//    }
//
//    public Integer[] getPageList(Integer curPageNum) {
//        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
//
//        // 총 게시글 갯수
//        Double postsTotalCount = Double.valueOf(this.getBoardCount());
//
//        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
//        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));
//
//        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
//        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
//                ? curPageNum + BLOCK_PAGE_NUM_COUNT
//                : totalLastPageNum;
//
//        // 페이지 시작 번호 조정
//        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;
//
//        // 페이지 번호 할당
//        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
//            pageList[idx] = val;
//        }
//
//        return pageList;
//    }


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
