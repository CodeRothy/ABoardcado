package com.rim.aboardcado.domain.repository;

import com.rim.aboardcado.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
