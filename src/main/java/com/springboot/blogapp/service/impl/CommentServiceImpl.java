package com.springboot.blogapp.service.impl;

import com.springboot.blogapp.dto.CommentDto;
import com.springboot.blogapp.entity.Comment;
import com.springboot.blogapp.entity.Post;
import com.springboot.blogapp.exception.BlogAPIException;
import com.springboot.blogapp.exception.ResourceNotFoundException;
import com.springboot.blogapp.repository.CommentRepository;
import com.springboot.blogapp.repository.PostRepository;
import com.springboot.blogapp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepo;
    @Autowired
    private ModelMapper mapper;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        Comment saveComment = commentRepository.save(comment);
        return mapToDto(saveComment);
    }

    @Override
    public List<CommentDto> getAllCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Comment comment = checkComment(postId, commentId);
        return mapToDto(comment);
    }
    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Comment comment = checkComment(postId, commentId);
        comment.setName(commentDto.getName() != null ? commentDto.getName() : comment.getName());
        comment.setEmail(commentDto.getEmail() != null ? commentDto.getEmail() : comment.getEmail());
        comment.setBody(commentDto.getBody() != null ? commentDto.getBody() : comment.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }
    @Override
    public void deleteComment(long postId, long commentId) {
        Comment comment = checkComment(postId, commentId);
        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment) {
        return mapper.map(comment,CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return mapper.map(commentDto,Comment.class);
    }

    private Comment checkComment(long postId, long commentId){
        Post post = postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to the Post");
        }
        return comment;
    }
}
