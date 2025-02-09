package com.example.myblog.service;

import com.example.myblog.dao.PostDao;
import com.example.myblog.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private PostDao postDao;

    public PostService(PostDao postDao) {
        this.postDao = postDao;
    }

    public List<Post> getAllPosts(int page, int size) {
        return postDao.findAll(page, size);
    }

    public Post getPostById(Long id) {
        return postDao.findById(id).orElseThrow(() -> new RuntimeException("Пост не найден"));
    }

    public void addPost(Post post) {
        postDao.save(post);
    }

    public void likePost(Long postId) {
        postDao.increaseLikeCount(postId);
    }

    public List<Post> getPostsByTag(String tag) {
        return postDao.findAllByTag(tag);
    }
}
