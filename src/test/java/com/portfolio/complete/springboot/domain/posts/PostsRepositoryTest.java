package com.portfolio.complete.springboot.domain.posts;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
//@Transactional
//@Rollback(value = false)
@Slf4j
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 저장 및 불러오기")
    void boardSaveAndFind() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("exam01@gmail.com")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }

    @Test
    @DisplayName("BaseTimeEntity 의 반영 여부")
    void BaseTimeEntityTest() {

        //given
        LocalDateTime now = LocalDateTime.of(2021, 11, 5, 0, 0, 0);

        postsRepository.save(Posts
                .builder()
                .title("title")
                .content("content")
                .author("author")
                .build()
        );

        //when
        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get(0);


        //then

        log.info("createDate = {}, modifiedDate = {}", posts.getCreateDate(), posts.getModifiedDate());
        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);

    }
}