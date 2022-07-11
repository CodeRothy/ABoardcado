package com.rim.aboardcado.domain.repository;

import com.rim.aboardcado.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
    Member findByName(String author);
}