package com.rim.aboardcado.service;

import com.rim.aboardcado.domain.entity.Member;
import com.rim.aboardcado.domain.repository.MemberRepository;
import com.rim.aboardcado.dto.BoardDto;
import com.rim.aboardcado.dto.MemberDto;
import com.rim.aboardcado.dto.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
//@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

//    public Long savePost(BoardDto boardDto) {
//
//        return boardRepository.save(boardDto.toEntity()).getId();
//    }


    public Member saveMember(MemberDto memberDto) {
        MemberChk(memberDto);

        return memberRepository.save(memberDto.toEntityEncode());
    }

    private void MemberChk(MemberDto memberDto) {
        Member findMember = memberRepository.findByEmail(memberDto.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

}
