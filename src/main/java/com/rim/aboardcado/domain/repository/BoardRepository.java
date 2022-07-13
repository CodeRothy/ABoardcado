package com.rim.aboardcado.domain.repository;

import com.rim.aboardcado.domain.entity.Board;
import com.rim.aboardcado.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board, Long> {

    // 페이징
    //Page<Board> findAll(Pageable pageable);

    // 검색 페이징
    Page<Board> findByTitleContainingOrContentContaining(String title,String content, Pageable pageable);



}
