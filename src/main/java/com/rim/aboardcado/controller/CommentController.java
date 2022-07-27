package com.rim.aboardcado.controller;

import com.rim.aboardcado.domain.entity.Comment;
import com.rim.aboardcado.domain.repository.CommentRepository;
import com.rim.aboardcado.dto.CommentDto;
import com.rim.aboardcado.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;


    // 댓글쓰기
    @PostMapping("/post/{id}")
    public ResponseEntity commentWrite(@PathVariable("id") Long id, @RequestBody CommentDto commentDto, Model model, @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();

        commentService.saveComment(commentDto, email, id);


        return ResponseEntity.ok(commentService.saveComment(commentDto, email, id));
    }


    // 댓글 수정
    @PutMapping("/post/{id}/comment/{commentId}")
    public String comEdit(@PathVariable("id") Long id,
                          @PathVariable("commentId")Long commentId, @RequestParam Map<String, Object> paramMap ) {



        Comment comment = commentRepository.findById(commentId).get();
        comment.update(paramMap.get("comment").toString());

        return "board/detail :: #commentTable";
    }
}
