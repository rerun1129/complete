package com.portfolio.complete1.springboot.dto;


import com.portfolio.complete1.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    @Builder
    public PostResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
