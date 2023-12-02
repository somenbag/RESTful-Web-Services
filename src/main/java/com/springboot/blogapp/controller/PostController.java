package com.springboot.blogapp.controller;

import com.springboot.blogapp.dto.PostDto;
import com.springboot.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPosts(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable("id") long postId,
            @RequestBody PostDto postDto
    ){
       return new ResponseEntity<>(postService.updatePost(postId,postDto),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post is successfully deleted with id :"+id,HttpStatus.OK);
    }
}
