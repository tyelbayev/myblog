package com.example.myblog.controller;

import com.example.myblog.model.Post;
import com.example.myblog.service.PostService;
import org.springframework.http.MediaType;
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
    public List<Post> getAllPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        return postService.getAllPosts(page, size);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addPost(@RequestBody Post post) {
        postService.addPost(post);
        return ResponseEntity.ok("Пост добавлен");
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable("id") Long id) {
        postService.likePost(id);
        return ResponseEntity.ok("Лайк поставлен");
    }

    @GetMapping("/filter")
    public List<Post> getPostsByTag(@RequestParam("tag") String tag) {
        return postService.getPostsByTag(tag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        postService.updatePost(id, post);
        return ResponseEntity.ok("Пост обновлен");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Пост удален");
    }

}
