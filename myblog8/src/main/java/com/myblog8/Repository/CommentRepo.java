package com.myblog8.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblog8.Entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {
	
	List<Comment> findByPostId(long postId);

}
