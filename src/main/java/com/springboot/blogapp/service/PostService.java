package com.springboot.blogapp.service;

import com.springboot.blogapp.dto.PostDto;
import com.springboot.blogapp.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize,String sortBy, String sortDir);

    PostDto getPostById(long postId);

    PostDto updatePost(long postId, PostDto postDto);

    void deletePostById(long id);


}
