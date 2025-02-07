package com.example.myblog.controller;

import com.example.myblog.model.Comment;
import com.example.myblog.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @PostMapping
    public ResponseEntity<String> addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
        return ResponseEntity.ok("Комментарий добавлен");
    }
}
