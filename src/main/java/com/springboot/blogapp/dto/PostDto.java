package com.springboot.blogapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

    private long id;
    @NotEmpty
    @NotBlank
    @Size(min=3,message = "Post Title should have at least 3 characters")
    private String title;
    @NotEmpty
    @NotBlank
    @Size(min = 10,message = "Post Description should have at least 3 characters")
    private String description;
    @NotEmpty
    @NotBlank
    private String content;
    private Set<CommentDto> comments;
}

