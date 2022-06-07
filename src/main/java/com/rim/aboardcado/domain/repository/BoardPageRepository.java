package com.rim.aboardcado.domain.repository;

import com.rim.aboardcado.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardPageRepository {

    Page<BoardDto> getPage(Pageable pageable);

}
