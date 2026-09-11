package com.example.servicerequest.repository;
import com.example.servicerequest.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CommentRepository extends JpaRepository<Comment, Long> {}