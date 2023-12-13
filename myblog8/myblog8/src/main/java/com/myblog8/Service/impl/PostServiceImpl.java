package com.myblog8.Service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.myblog8.Entity.Post;
import com.myblog8.Exception.ResourceNotFoundException;
import com.myblog8.Payload.PostDto;
import com.myblog8.Payload.PostResponse;
import com.myblog8.Repository.PostRepo;
import com.myblog8.Service.PostService;


@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto savePost(PostDto postDto) {

		Post post = mapToEntity(postDto);

		Post savePost = this.postRepo.save(post);

		PostDto dto = mapToDto(savePost);

		return dto;
	}

	@Override
	public void deletePost(long id) {
		this.postRepo.deleteById(id);

	}

	@Override
	public PostDto updatePost(long id, PostDto postDto) {
		Post post = this.postRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with id : " + id));

		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());

		Post updatedpost = this.postRepo.save(post);
		PostDto dto = this.mapToDto(updatedpost);
		return dto;

	}

	@Override
	public PostDto getPostById(long id) {
		Post post = this.postRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with id :" + id));

		PostDto dto = this.mapToDto(post);
		return dto;

	}

	@Override
	public PostResponse getAllPost(int pageNo, int pageSize, String sortBy,String sortDir) {
		Sort sort = null;

		if (sortDir.equalsIgnoreCase("asc")) {

			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDto = posts.stream().map(post -> this.mapToDto(post)).collect(Collectors.toList());
		
		
		PostResponse postResponse = new PostResponse();
		postResponse.setPostDto(postDto);
		postResponse.setPageNo(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPages(pagePost.isLast());
		
		
		return postResponse;
	}

	PostDto mapToDto(Post post) {
//		PostDto dto = new PostDto();
//		dto.setId(post.getId());
//		dto.setTitle(post.getTitle());
//		dto.setDescription(post.getDescription());
//		dto.setContent(post.getContent());
//		return dto;
		
		
	    return this.modelMapper.map(post, PostDto.class);
	}

	Post mapToEntity(PostDto postDto) {

//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
//
//		return post;
		
		return this.modelMapper.map(postDto, Post.class);
	}

}
