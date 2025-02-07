package com.example.myblog.controller;

import com.example.myblog.model.Post;
import com.example.myblog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<String> addPost(@RequestBody Post post) {
        postService.addPost(post);
        return ResponseEntity.ok("Пост добавлен");
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable Long id) {
        postService.likePost(id);
        return ResponseEntity.ok("Лайк поставлен");
    }
}
