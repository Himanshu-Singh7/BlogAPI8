package com.myblog8.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myblog8.Payload.CommentDto;

@Service
public interface CommentService {

	CommentDto createComment(long postId, CommentDto commentDto);
	
	void deleteComment(long commentId);
	
	List<CommentDto> getCommentsByPostId(long postId);

	CommentDto getCommentsById(long postId,long commentId);

	List<CommentDto> getAllCommentsById();
	
}
