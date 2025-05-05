package test.java.com.example.myblog.controller;

import main.java.com.example.myblog.controller.PostController;
import test.java.com.example.myblog.config.TestDatabaseConfig;
import main.java.com.example.myblog.model.Comment;
import main.java.com.example.myblog.model.Post;
import main.java.com.example.myblog.service.CommentService;
import main.java.com.example.myblog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class PostControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PostService postService;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private PostController postController;

    private Post testPost;
    private List<Comment> testComments;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();

        testPost = new Post();
        testPost.setId(1L);
        testPost.setTitle("Test Title");
        testPost.setContent("Test Content");
        testPost.setImgUrl("test.png");
        testPost.setCreatedAt(LocalDateTime.now());
        testPost.setTags("test");

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setPostId(1L);
        comment.setContent("Test Comment");
        comment.setCreatedAt(LocalDateTime.now());
        testComments = Collections.singletonList(comment);
    }

    @Test
    public void testGetPostById() throws Exception {
        when(postService.getPostById(1L)).thenReturn(testPost);
        when(commentService.getCommentsByPostId(1L)).thenReturn(testComments);

        mockMvc.perform(get("/post/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(model().attribute("post", testPost))
                .andExpect(model().attribute("comments", testComments));
    }
}
