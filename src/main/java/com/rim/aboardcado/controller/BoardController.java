package com.rim.aboardcado.controller;

import com.rim.aboardcado.domain.entity.Board;
import com.rim.aboardcado.domain.repository.BoardRepository;
import com.rim.aboardcado.dto.BoardDto;
import com.rim.aboardcado.service.BoardService;
import com.rim.aboardcado.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

//@RequestMapping("/board")
@Controller
@AllArgsConstructor
public class BoardController {

    private BoardService boardService;
    private BoardRepository boardRepository;
    private MemberService memberService;


    // 리스트 페이징
    @GetMapping("/")
    public String boardList(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Board> boards = boardRepository.findAll(pageable);
        List<BoardDto> boardList = boardService.getBoardList(pageable);

        int nowPage = boards.getPageable().getPageNumber()+1;
        int startPage = Math.max(nowPage-4,1);
        int endPage =   Math.min(nowPage+5, boards.getTotalPages());
        int totalPages = boards.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);

        return "board/list";


    }

    // 검색 페이징
    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword") String keyword,
                         @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC)
                                 Pageable pageable, Model model) {
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
        List<BoardDto> boardList = boardService.searchPosts(keyword, keyword, pageable);

        int nowPage = boards.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, boards.getTotalPages());
        int totalPages = boards.getTotalPages();

        model.addAttribute("Keyword", keyword);
        model.addAttribute("boardList", boardList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);

        return "board/list";
    }

    // 글쓰기
    @GetMapping("/post")
    public String post(BoardDto boardDto, @AuthenticationPrincipal UserDetails userDetails, Model model) {

        String author = memberService.findByEmail(userDetails.getUsername()).getName();

        model.addAttribute("author", author);
        model.addAttribute("board", boardDto);

        return "board/post";
    }
    //
    @PostMapping("/post")
    public String write(@Valid BoardDto boardDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (bindingResult.hasErrors()) {
            return "board/post";
        }
        try {
            // UserDetails 상속, username = email 을 override 해두었음 (MemberService)
            String email = userDetails.getUsername();
            boardService.savePost(boardDto, email);

        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "board/post";
        }

        return "redirect:/"; // redirect 경로 확인 (requestMapping /board 포함하는지)
    }


    // 글 수정
    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails, Model model){
        BoardDto boardDto = boardService.postDtl(id);

        String email = userDetails.getUsername();
        //boardService.idCheck(boardDto, email);
        if (!boardService.idCheck(boardDto, email)){
            return "redirect:/post/{id}";
        }

        model.addAttribute("board", boardDto);
        return "board/edit";
    }

    //@PreAuthorize("isAuthenticated() and (( # == authentication.name ) or hasRole('ROLE_ADMIN'))")
    @PutMapping("/post/edit/{id}")
    public String update(@Valid BoardDto boardDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (bindingResult.hasErrors()) {
            return "board/edit";
        }
        try {
            String email = userDetails.getUsername();
            boardService.savePost(boardDto, email);

        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "board/edit";
        }

        return "redirect:/";
    }


    // 글 상세보기
    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        BoardDto boardDto = boardService.postDtl(id);
        model.addAttribute("board", boardDto);
        return "board/detail";
    }


    // 글 삭제
    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.deletePost(id);
        return "redirect:/";
    }

}
