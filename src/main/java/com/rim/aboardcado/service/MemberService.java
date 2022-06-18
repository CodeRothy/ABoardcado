package com.rim.aboardcado.service;

import com.rim.aboardcado.domain.entity.Board;
import com.rim.aboardcado.domain.entity.Member;
import com.rim.aboardcado.domain.repository.MemberRepository;
import com.rim.aboardcado.dto.BoardDto;
import com.rim.aboardcado.dto.MemberDto;
import com.rim.aboardcado.dto.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
//@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public Member saveMember(MemberDto memberDto) {
        memberCheck(memberDto);

        return memberRepository.save(toEntity(memberDto));
    }

    private void memberCheck(MemberDto memberDto) {
        Member findMember = memberRepository.findByEmail(memberDto.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Transactional
    private Member toEntity(MemberDto memberDto) {
        return Member.builder()
                .name(memberDto.getName())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .email(memberDto.getEmail())
                .role(Role.USER)
                .build();
    }

}
