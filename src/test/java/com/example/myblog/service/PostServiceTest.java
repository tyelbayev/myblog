package com.example.myblog.service;

import com.example.myblog.config.TestDatabaseConfig;
import com.example.myblog.dao.PostDao;
import com.example.myblog.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig
@ContextConfiguration(classes = {TestDatabaseConfig.class, PostService.class, PostDao.class})
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("DELETE FROM post");  // Очищаем таблицу перед каждым тестом

        jdbcTemplate.update("INSERT INTO post (title, content, image_url, like_count, tags) " +
                        "VALUES (?, ?, ?, ?, ?)",
                "Test Title", "Test Content", "test.png", 0, "test");
    }

    @Test
    public void testGetAllPosts() {
        List<Post> posts = postService.getAllPosts(0, 10);
        assertNotNull(posts);
        assertEquals(1, posts.size());
        assertEquals("Test Title", posts.get(0).getTitle());
    }

    @Test
    public void testGetPostById() {
        Post post = postService.getPostById(1L);
        assertNotNull(post);
        assertEquals("Test Title", post.getTitle());
    }

    @Test
    public void testAddPost() {
        Post newPost = new Post();
        newPost.setTitle("New Test Post");
        newPost.setContent("New test content");
        newPost.setImgUrl("new_image.png");
        newPost.setTags("new_tag");

        postService.addPost(newPost);

        List<Post> posts = postService.getAllPosts(0, 10);
        assertEquals(2, posts.size());
        assertEquals("New Test Post", posts.get(1).getTitle());
    }

    @Test
    public void testLikePost() {
        postService.likePost(1L);

        Post updatedPost = postService.getPostById(1L);
        assertEquals(1, updatedPost.getLikeCount());
    }

    @Test
    public void testUpdatePost() {
        Post updatedPost = new Post();
        updatedPost.setTitle("Updated Title");
        updatedPost.setContent("Updated content");
        updatedPost.setImgUrl("updated_image.png");
        updatedPost.setTags("updated_tag");

        postService.updatePost(1L, updatedPost);

        Post postAfterUpdate = postService.getPostById(1L);
        assertEquals("Updated Title", postAfterUpdate.getTitle());
        assertEquals("Updated content", postAfterUpdate.getContent());
        assertEquals("updated_image.png", postAfterUpdate.getImgUrl());
        assertEquals("updated_tag", postAfterUpdate.getTags());
    }

    @Test
    public void testDeletePost() {
        postService.deletePost(1L);

        List<Post> posts = postService.getAllPosts(0, 10);
        assertEquals(0, posts.size());
    }

}
