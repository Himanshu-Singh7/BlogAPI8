package com.myblog8.Service;

import java.util.List;

import com.myblog8.Payload.PostDto;
import com.myblog8.Payload.PostResponse;

public interface PostService {

	PostDto savePost(PostDto postDto);

	void deletePost(long id);

	PostDto updatePost(long id, PostDto postDto);

	PostDto getPostById(long id);
    
	PostResponse getAllPost(int pageNo, int pageSize, String sortBy,String sortDir );

	

	
}
