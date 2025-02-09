package com.example.myblog.dao;

import com.example.myblog.model.Post;
import org.h2.result.Row;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostDao {
    private final JdbcTemplate jdbcTemplate;

    public PostDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public final RowMapper<Post> postRowMapper = (rs, rowNum) -> {
        Post post = new Post();
        post.setId(rs.getLong("id"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setImgUrl(rs.getString("image_url"));
        post.setLikeCount(rs.getInt("like_count"));
        post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        post.setTags(rs.getString("tags"));
        return post;
    };

    public List<Post> findAll() {
        String sql = "select * from post";
        return jdbcTemplate.query(sql, postRowMapper);
    }

    public Optional<Post> findById(Long id) {
        String sql = "SELECT * FROM post WHERE id = ?";
        List<Post> posts = jdbcTemplate.query(sql, postRowMapper, id);
        return posts.isEmpty() ? Optional.empty() : Optional.of(posts.get(0));
    }

    public void increaseLikeCount(long postId) {
        String sql = "update post set like_count = like_count + 1 where id = ?";
        jdbcTemplate.update(sql, postId);
    }

    public void save(Post post) {
        String sql = "INSERT INTO post (title, content, image_url, created_at, like_count, tags) VALUES (?, ?, ?, NOW(), 0, ?)";
        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getImgUrl(), post.getTags());
    }
}
