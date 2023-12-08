package com.myblog8.Service.impl;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.myblog8.Entity.Comment;
import com.myblog8.Entity.Post;
import com.myblog8.Exception.ResourceNotFoundException;
import com.myblog8.Payload.CommentDto;
import com.myblog8.Repository.CommentRepo;
import com.myblog8.Repository.PostRepo;
import com.myblog8.Service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CommentRepo commentRepo;
	

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		
		Comment comment = this.mapToEntity(commentDto);
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post Not Found By Id :" + postId));
		comment.setPost(post);
		Comment saveComment = this.commentRepo.save(comment);
		CommentDto dto = this.mapToDto(saveComment);
		return dto;
	}
	
	@Override
	public void deleteComment(long commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Commment id is not found : "+commentId));
		this.commentRepo.delete(comment);
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post Not Found By Id :" + postId));
		List<Comment> comments = commentRepo.findByPostId(postId);
		List<CommentDto> collectDto = comments.stream().map(comment -> this.mapToDto(comment)).collect(Collectors.toList());
		
		return collectDto;
	}
	
	
	@Override
	public CommentDto getCommentsById(long postId,long commentId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post Not Found By Id :" + postId));
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment Id are Not Found By Id: " + commentId));
		
		CommentDto commentDto = this.mapToDto(comment);
		return commentDto;
	}
	
	@Override
	public List<CommentDto> getAllCommentsById() {
		List<Comment> comments = this.commentRepo.findAll();
		List<CommentDto> commentdto = comments.stream().map(comment -> this.mapToDto(comment)).collect(Collectors.toList());
		return commentdto;
	}


	
	
	private Comment mapToEntity(CommentDto commentDto) {
		 
		return this.modelMapper.map(commentDto,Comment.class);
	}
	
	private CommentDto mapToDto(Comment comment) {
		return this.modelMapper.map(comment, CommentDto.class);
	}

	
	
	

	



	
}
