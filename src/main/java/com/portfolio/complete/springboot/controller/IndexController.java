package com.portfolio.complete.springboot.controller;

import com.portfolio.complete.springboot.dto.PostResponseDto;
import com.portfolio.complete.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("posts", postsService.findAllDesc());

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {

        PostResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);        //여기서 post 라는 키로 보내기 때문에 posts-update 에서 post.~ 로 받는다.

        return "posts-update";
    }



}
