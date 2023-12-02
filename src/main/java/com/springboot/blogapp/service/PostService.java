package com.springboot.blogapp.service;

import com.springboot.blogapp.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(long postId);

    PostDto updatePost(long postId, PostDto postDto);

    void deletePostById(long id);
}
