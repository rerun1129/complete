package com.portfolio.complete1.springboot.controller;

import com.portfolio.complete1.springboot.config.auth.LoginUser;
import com.portfolio.complete1.springboot.config.auth.dto.SessionUser;
import com.portfolio.complete1.springboot.dto.PostResponseDto;
import com.portfolio.complete1.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) { //세션을 직접 가져오던 것을 개선함 이 어노테이션에 SessionUser만 붙으면 그 파라미터는 세션을 담고 움직임.

        model.addAttribute("posts", postsService.findAllDesc());

        //SessionUser loginUser = (SessionUser) httpSession.getAttribute("user");      //로그인 성공 시에 loginUser 에서 값을 가져올 수 있다.

        if (user != null) { //null 체크와 화면에 머스태치에서 userName 을 띄울지 말지를 결정할 attribute 를 담아서 보내준다. 머스태치에서는 userName 으로 루프를 돌리니까 키는 userName 으로 보내줘야 받는다.
            model.addAttribute("userName", user.getName());
        }       //null 체크가 false 가 되면 값을 보낼 것이 없기 때문에 userName 을 머스태치가 null 로 받을 것이고 화면은 ^상태의 로그인 화면이 보일 것이다.

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
