package test.java.com.example.myblog.service;

import main.java.com.example.myblog.service.CommentService;
import test.java.com.example.myblog.config.TestDatabaseConfig;
import main.java.com.example.myblog.dao.CommentDao;
import main.java.com.example.myblog.model.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig
@ContextConfiguration(classes = {TestDatabaseConfig.class, CommentService.class, CommentDao.class})
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("DELETE FROM comment");  // Очищаем таблицу перед каждым тестом

        jdbcTemplate.update("INSERT INTO comment (post_id, content, created_at) " +
                        "VALUES (?, ?, ?)",
                1L, "Test Comment", LocalDateTime.now());
    }

    @Test
    public void testAddComment() {
        Comment newComment = new Comment();
        newComment.setPostId(1L);
        newComment.setContent("New test comment");
        newComment.setCreatedAt(LocalDateTime.now());

        commentService.addComment(newComment);

        List<Comment> comments = commentService.getCommentsByPostId(1L);
        assertEquals(2, comments.size());
        assertEquals("New test comment", comments.get(1).getContent());
    }

    @Test
    public void testGetCommentsByPostId() {
        List<Comment> comments = commentService.getCommentsByPostId(1L);
        assertNotNull(comments);
        assertEquals(1, comments.size());
        assertEquals("Test Comment", comments.get(0).getContent());
    }

    @Test
    public void testUpdateComment() {
        Comment updatedComment = new Comment();
        updatedComment.setPostId(1L);
        updatedComment.setContent("Updated Comment");
        updatedComment.setCreatedAt(LocalDateTime.now());

        commentService.updateComment(1L, updatedComment);

        List<Comment> comments = commentService.getCommentsByPostId(1L);
        assertEquals(1, comments.size());
        assertEquals("Updated Comment", comments.get(0).getContent());
    }

    @Test
    public void testDeleteComment() {
        commentService.deleteComment(1L);

        List<Comment> comments = commentService.getCommentsByPostId(1L);
        assertEquals(0, comments.size());
    }

}
