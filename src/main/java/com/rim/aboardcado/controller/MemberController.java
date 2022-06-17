package com.rim.aboardcado.controller;

import com.rim.aboardcado.domain.entity.Member;
import com.rim.aboardcado.dto.MemberDto;
import com.rim.aboardcado.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup( ) {
        //model.addAttribute("memberDto", new memberDto());
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(MemberDto memberDto) {

        memberService.saveMember(memberDto);
        return "redirect:/";
    }


}
