package com.rim.aboardcado.domain.repository;

import com.rim.aboardcado.domain.entity.Board;
import com.rim.aboardcado.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {

    //Page<Board> findAll(Pageable pageable);

}
