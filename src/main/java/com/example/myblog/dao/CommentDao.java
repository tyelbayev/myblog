package com.example.myblog.dao;

import com.example.myblog.model.Comment;
import com.example.myblog.model.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.chrono.JapaneseDate;
import java.util.List;

@Repository
public class CommentDao {
    private final JdbcTemplate jdbcTemplate;

    public CommentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Comment> findByPostId(Long postId) {
        String sql = "select * from comment where post_id = ?";

        return jdbcTemplate.query(sql, new Object[]{postId}, (rs, rowNum) -> {
            Comment comment = new Comment();
            comment.setId(rs.getLong("id"));
            comment.setPostId(rs.getLong("post_id"));
            comment.setContent(rs.getString("content"));
            return comment;
        });
    }

    public void save(Comment comment) {
        String sql = "INSERT INTO comment (post_id, content, created_at) VALUES (?, ?, NOW())";
        jdbcTemplate.update(sql, comment.getPostId(), comment.getContent());
    }
}
