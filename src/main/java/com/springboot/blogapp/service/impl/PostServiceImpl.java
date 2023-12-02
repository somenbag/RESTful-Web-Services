package com.springboot.blogapp.service.impl;

import com.springboot.blogapp.dto.PostDto;
import com.springboot.blogapp.entity.Post;
import com.springboot.blogapp.exception.ResourceNotFoundException;
import com.springboot.blogapp.repository.PostRepository;
import com.springboot.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepo;
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = Post.builder()
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .content(postDto.getContent())
                .build();
        Post savePost = postRepo.save(post);
        return PostDto.builder()
                .id(savePost.getId())
                .title(savePost.getTitle())
                .content(savePost.getContent())
                .description(savePost.getDescription())
                .build();
    }
    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepo.findAll();
        return posts.stream().
                map((post) -> mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId));
        return mapToDto(post);

    }

    @Override
    public PostDto updatePost(long postId, PostDto postDto) {
        Post post = postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId));
        post.setTitle(postDto.getTitle()!=null? postDto.getTitle() : post.getTitle());
        post.setDescription(postDto.getDescription()!=null? postDto.getDescription() : post.getDescription());
        post.setContent(postDto.getContent()!=null? postDto.getContent() : post.getContent());
        Post updatedPost = postRepo.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", id));
        postRepo.delete(post);
    }

    private PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }
    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
