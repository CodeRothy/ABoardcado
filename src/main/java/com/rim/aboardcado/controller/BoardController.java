package com.rim.aboardcado.controller;

import com.rim.aboardcado.dto.BoardDto;
import com.rim.aboardcado.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequestMapping("/board")
@Controller
@AllArgsConstructor
public class BoardController {

    private BoardService boardService;

//    public BoardController(BoardService boardService) {
//        this.boardService = boardService;
//    } @AllArgsConstructor 로 생성자 생성 대체

    @GetMapping("/")
    public String list(Model model) {
        List<BoardDto> boardDtoList = boardService.getBoardList();
        model.addAttribute("postList", boardDtoList);
        return "board/list";
    }

    @GetMapping("/post")
    public String post() {

        return "board/post";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/"; // redirect 경로 확인 (requestMapping /board 포함하는지)
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        BoardDto boardDto = boardService.postDtl(id);
        model.addAttribute("post", boardDto);
        return "board/detail";
    }

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

}
