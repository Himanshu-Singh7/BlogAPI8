package com.myblog8.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.myblog8.Payload.PostDto;
import com.myblog8.Payload.PostResponse;
import com.myblog8.Service.PostService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/posts")
public class PostController {
	@Autowired
	private PostService postService;

	// http://localhost:8182/api/posts/save

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<?> savePost(@Valid @RequestBody PostDto postDto, BindingResult result) {
		if (result.hasErrors()) {
	        String errorMessage = "Invalid data provided. Please check the following fields:";
	        List<FieldError> errors = result.getFieldErrors();
	        for (FieldError error : errors) {
	            errorMessage += "\n" + error.getField() + ": " + error.getDefaultMessage();
	        }
	        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	    }
		PostDto savePost = this.postService.savePost(postDto);
		return new ResponseEntity<>(savePost, HttpStatus.CREATED);// 201

	}
	// http://localhost:8182/api/posts/id
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable("id") long id) {
		this.postService.deletePost(id);
		return new ResponseEntity<String>("post is deleted", HttpStatus.OK); // 200
	}
	// http://localhost:8182/api/posts/id

	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @PathVariable("id") long id, @RequestBody PostDto postDto) {
		PostDto updatePost = this.postService.updatePost(id, postDto);

		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	// http://localhost:8182/api/posts/id
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {
		PostDto dto = this.postService.getPostById(id);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	// http://localhost:8182/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=desc
	@GetMapping
	public PostResponse getAllPost(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

	) {
		PostResponse postResponse = this.postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
		return postResponse;
	}

}
