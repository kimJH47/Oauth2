package com.oauth2.exam.web;


import com.oauth2.exam.config.auth.LoginUser;
import com.oauth2.exam.config.auth.dto.SessionUser;
import com.oauth2.exam.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
//WebController
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    //main page
    @GetMapping("/")
    public String main(Model model, @LoginUser SessionUser user) {

        model.addAttribute("posts", postsService.findAllDesc());

        /**
         * CustomOAuth2Service 에서 로그인 성공시 세션을 SessionUser 클래스에 저장
         * .getAttribute("user") 를 통하여 값 가져오기 가능
         */
        //SessionUser user = (SessionUser)httpSession.getAttribute("user");


        /**
         * 로그인 성공시에만 userName 등록
         */
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "post-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("post", postsService.findById(id));
        return "posts-update";
    }

}
