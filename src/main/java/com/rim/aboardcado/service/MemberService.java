package com.rim.aboardcado.service;

import com.rim.aboardcado.domain.entity.Member;
import com.rim.aboardcado.domain.repository.MemberRepository;
import com.rim.aboardcado.dto.MemberDto;
import com.rim.aboardcado.dto.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

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
    public Member toEntity(MemberDto memberDto) {
        return Member.builder()
                .name(memberDto.getName())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .email(memberDto.getEmail())
                .role(Role.USER)
                .build();
    }

    // 로그인
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getName())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

//    // id 찾기
//    public int findId(String email){
//
//        int memberId = memberRepository.findByEmail(email).getId().intValue();
//
//        return memberId;
//    }

}