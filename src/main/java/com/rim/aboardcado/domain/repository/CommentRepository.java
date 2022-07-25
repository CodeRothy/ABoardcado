package com.rim.aboardcado.domain.repository;

import com.rim.aboardcado.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
