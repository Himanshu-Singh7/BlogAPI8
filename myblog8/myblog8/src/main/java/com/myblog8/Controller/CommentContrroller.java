package com.myblog8.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.myblog8.Payload.CommentDto;
import com.myblog8.Service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentContrroller {

	@Autowired
	private CommentService commentService;
	// http://localhost:8182/api/posts/1/comments

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createCommentForPost(@PathVariable("postId") long postId,
			@RequestBody CommentDto commentDto) {
		CommentDto createComment = this.commentService.createComment(postId, commentDto);
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);

	}

	// http://localhost:8182/api/1

	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> deleteCommentById(@PathVariable("commentId") long commentId) {
		commentService.deleteComment(commentId);
		return ResponseEntity.ok().build();
	}

	
	
	
	// http://localhost:8182/api/posts/1/comments
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable("postId") long postId) {

		return this.commentService.getCommentsByPostId(postId);
	}

	// http://localhost:8182/api/posts/1/comments/2
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentsId(@PathVariable("postId") long postId,
			@PathVariable("commentId") long commentId) {
		CommentDto commentDto = this.commentService.getCommentsById(postId, commentId);

		return new ResponseEntity<CommentDto>(commentDto, HttpStatus.OK);
	}

	// http://localhost:8182/api/comments
	@GetMapping("/comments")
	public ResponseEntity<List<CommentDto>> getAllCommentsByID() {
		List<CommentDto> allComments = this.commentService.getAllCommentsById();

		return new ResponseEntity<List<CommentDto>>(allComments, HttpStatus.OK);
	}
}
