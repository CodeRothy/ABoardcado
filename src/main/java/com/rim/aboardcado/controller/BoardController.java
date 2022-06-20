package com.rim.aboardcado.controller;

import com.rim.aboardcado.domain.entity.Board;
import com.rim.aboardcado.domain.repository.BoardRepository;
import com.rim.aboardcado.dto.BoardDto;
import com.rim.aboardcado.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@RequestMapping("/board")
@Controller
@AllArgsConstructor
public class BoardController {

    private BoardService boardService;
    private BoardRepository boardRepository;


    // 리스트
    @GetMapping("/")
    public String boardList(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC)
                                        Pageable pageable, Model model) {

        Page<Board> page = boardRepository.findAll(pageable);
        List<BoardDto> boardList = boardService.getBoardList(pageable);

        int nowPage = page.getPageable().getPageNumber()+1;
        int startPage = Math.max(nowPage-4,1);
        int endPage = Math.min(nowPage+5, page.getTotalPages());
        int totalPages = page.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);

        return "board/list";
    }

    // 글 검색
    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword") String keyword,
                         @PageableDefault(page = 0 , size = 5, sort = "id", direction = Sort.Direction.DESC)
                                 Pageable pageable, Model model) {

        Page<Board> page = boardRepository.findByTitleContainingOrContentContaining(keyword,keyword,pageable);
        List<BoardDto> boardList = boardService.searchPosts(keyword,keyword, pageable);


        //Page<Board> page = boardRepository.findAll(pageable);
        //List<BoardDto> boardList = boardService.getBoardList(pageable);

        int nowPage = page.getPageable().getPageNumber()+1;
        int startPage = Math.max(nowPage-4,1);
        int endPage = Math.min(nowPage+5, page.getTotalPages());
        int totalPages = page.getTotalPages();

        model.addAttribute("keyword", keyword);
        model.addAttribute("boardList", boardList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);

        return "board/list";
    }


    // 글쓰기
    @GetMapping("/post")
    public String post(BoardDto boardDto, Model model) {
        model.addAttribute("boardDto", boardDto);

        return "board/post";
    }


    @PostMapping("/post")
    public String write(@Valid BoardDto boardDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "board/post";
        }
        try {
            boardService.savePost(boardDto);

        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "board/post";
        }

        return "redirect:/"; // redirect 경로 확인 (requestMapping /board 포함하는지)
    }


    // 글 상세보기
    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        BoardDto boardDto = boardService.postDtl(id);
        model.addAttribute("post", boardDto);
        return "board/detail";
    }


    // 글 수정
    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        BoardDto boardDto = boardService.postDtl(id);
        model.addAttribute("post", boardDto);
        return "board/edit";
    }

    @PutMapping("/post/edit/{id}")
    public String update(BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/post/{id}";
    }


    // 글 삭제
    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.deletePost(id);
        return "redirect:/";
    }

}
