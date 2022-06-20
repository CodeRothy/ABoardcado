package com.rim.aboardcado.controller;

import com.rim.aboardcado.dto.MemberDto;
import com.rim.aboardcado.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String join( ) {
        return "member/join";
    }

    @PostMapping("/join")
    public String join(MemberDto memberDto) {

        memberService.saveMember(memberDto);
        return "redirect:/";
    }


}