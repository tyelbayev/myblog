package main.java.com.example.myblog.controller;

import main.java.com.example.myblog.model.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import main.java.com.example.myblog.service.PostService;

import java.util.List;

@Controller
public class HomeController {

    private final PostService postService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size) {

        List<Post> posts = postService.getAllPosts(page, size);
        model.addAttribute("posts", posts);

        return "main/webapp/WEB-INF/views/index.html";
    }
}

