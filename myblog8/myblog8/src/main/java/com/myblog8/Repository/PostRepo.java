package com.myblog8.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblog8.Entity.Post;

public interface PostRepo extends JpaRepository<Post, Long> {

}
