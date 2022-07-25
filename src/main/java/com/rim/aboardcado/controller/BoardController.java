package com.rim.aboardcado.controller;

import com.rim.aboardcado.domain.entity.Board;
import com.rim.aboardcado.domain.entity.Comment;
import com.rim.aboardcado.domain.repository.BoardRepository;
import com.rim.aboardcado.domain.repository.CommentRepository;
import com.rim.aboardcado.domain.repository.MemberRepository;
import com.rim.aboardcado.dto.BoardDto;
import com.rim.aboardcado.dto.CommentDto;
import com.rim.aboardcado.service.BoardService;
import com.rim.aboardcado.service.CommentService;
import com.rim.aboardcado.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

//@RequestMapping("/board")
@Controller
@AllArgsConstructor
public class BoardController {

    private BoardService boardService;
    private BoardRepository boardRepository;
    private MemberService memberService;
    private MemberRepository memberRepository;
    private CommentService commentService;
    private CommentRepository commentRepository;


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
        int startPage = Math.max(1, nowPage - 4);
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

    // 글쓰기 페이지
    @GetMapping("/post")
    public String post(BoardDto boardDto, @AuthenticationPrincipal UserDetails userDetails, Model model) {

        String author = memberService.findByEmail(userDetails.getUsername()).getName();

        model.addAttribute("author", author);
        model.addAttribute("board", boardDto);

        return "board/post";
    }
    // 글 쓰기 로직
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

    // 댓글쓰기
    @PostMapping("/post/{id}")
    public String commentWrite(@PathVariable("id") Long id, CommentDto commentDto, Model model, @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        commentService.saveComment(commentDto, email, id);


        return "redirect:/post/{id}";
    }


    // 글 상세보기
    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {

        // 게시글 정보
        BoardDto boardDto = boardService.postDtl(id);

        // 로그인 회원 정보
        String email = userDetails.getUsername();

        // 댓글 정보
        List<Comment> commentList = boardRepository.findById(id).get().getCommentList();
        System.out.println("commentList if문 타기 전 : "+commentList);

        if (commentList !=null && !commentList.isEmpty()) {

//                // 수정, 삭제 버튼 표시를 위한 멤버체크
//                Long memberId = memberRepository.findByEmail(email).getId();
//                if (boardService.idCheck(boardDto, email)) {
//                    model.addAttribute("memberChk", "OK");
//                }
            System.out.println("commentList if문 : "+commentList);
            model.addAttribute("commentList", commentList);
        }


        // 수정, 삭제 버튼 표시를 위한 멤버체크
        if (boardService.idCheck(boardDto, email)){
            model.addAttribute("memberChk", "OK");
        }


        model.addAttribute("board", boardDto);
        return "board/detail";
    }


    // 글 수정
    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails, Model model){

        BoardDto boardDto = boardService.postDtl(id);
        String email = userDetails.getUsername();

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



    // 글 삭제
    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id, BoardDto boardDto, @AuthenticationPrincipal UserDetails userDetails ) {

        String email = userDetails.getUsername();

        if (!boardService.idCheck(boardDto, email)){
            return "redirect:/post/{id}";
        }
        boardService.deletePost(id);

        return "redirect:/";
    }


}
