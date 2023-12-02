package com.springboot.blogapp.service;

import com.springboot.blogapp.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId,CommentDto commentDto);

    List<CommentDto> getAllCommentByPostId(long postId);

    CommentDto getCommentById(long postId,long commentId);

    CommentDto updateComment(long postId,long commentId,CommentDto commentDto);

    void deleteComment(long postId,long commentId);
}
