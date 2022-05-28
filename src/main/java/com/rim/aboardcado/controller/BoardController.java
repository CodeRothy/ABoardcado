package com.rim.aboardcado.controller;

import com.rim.aboardcado.dto.BoardDto;
import com.rim.aboardcado.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@RequestMapping("/board")
@Controller
@AllArgsConstructor
public class BoardController {

    private BoardService boardService;

//    public BoardController(BoardService boardService) {
//        this.boardService = boardService;
//    }

    @GetMapping("/")
    public String list() {
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
}
