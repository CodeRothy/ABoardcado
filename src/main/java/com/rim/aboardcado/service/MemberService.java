package com.rim.aboardcado.service;

import com.rim.aboardcado.domain.entity.Member;
import com.rim.aboardcado.domain.repository.MemberRepository;
import com.rim.aboardcado.dto.MemberDto;
import com.rim.aboardcado.dto.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public Member saveMember(MemberDto memberDto) {

        // email 중복체크
        Member findMember = memberRepository.findByEmail(memberDto.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

        return memberRepository.save(toEntity(memberDto));
    }

    // MemberDto -> Member
    private Member toEntity(MemberDto memberDto) {
        return Member.builder()
                .name(memberDto.getName())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .email(memberDto.getEmail())
                .role(Role.USER)
                .build();
    }
}