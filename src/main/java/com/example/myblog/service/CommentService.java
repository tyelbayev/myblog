package com.example.myblog.service;

import com.example.myblog.dao.CommentDao;
import com.example.myblog.model.Comment;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {
    private final CommentDao commentDao;

    public CommentService(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentDao.findByPostId(postId);
    }

    public void addComment(Comment comment) {
        commentDao.save(comment);
    }
}
