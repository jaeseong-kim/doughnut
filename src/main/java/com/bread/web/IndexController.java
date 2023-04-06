package com.bread.web;

import com.bread.config.auth.LoginUser;
import com.bread.config.auth.dto.SessionUser;
import com.bread.service.posts.PostsService;
import com.bread.web.dto.PostsResponseDto;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

  private final PostsService postsService;

  @GetMapping("/")
  public String index(Model model, @LoginUser SessionUser user) {
    model.addAttribute("posts", postsService.findAllDesc());

    if (user != null) {
      log.info(user.getName());
      model.addAttribute("userName", user.getName());
    }
    return "index";
  }

  @GetMapping("/posts/save")
  public String postsSave() {
    return "posts-save";
  }

  @GetMapping("/posts/update/{id}")
  public String postsUpdate(@PathVariable Long id, Model model) {

    PostsResponseDto dto = postsService.findById(id);
    model.addAttribute("post", dto);

    return "posts-update";
  }
}
