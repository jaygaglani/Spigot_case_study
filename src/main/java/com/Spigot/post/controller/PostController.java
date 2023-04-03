package com.Spigot.post.controller;

import com.Spigot.post.dto.PostOutput;
import com.Spigot.post.dto.PostDetails;
import com.Spigot.post.dto.PostInput;
import com.Spigot.post.model.Post;
import com.Spigot.post.persistence.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class PostController {

    @Autowired
    private PostDao postDao;

    @GetMapping("/getAllPosts")
    public List<PostOutput> getAllPosts() {
        final List<Post> posts = postDao.getAllPosts();
        return posts.stream().map(post -> post.toPostOutput())
                .collect(Collectors.toList());
    }

    @GetMapping("/getPostByVertical")
    public List<PostDetails> getPostByVertical(String packageName){
        return postDao.getPostByPackage(packageName);
    }

    @GetMapping("/getPost")
    public PostOutput getPost(int id) {
       final Post post = postDao.getPostById(id);
       return Objects.isNull(post) ? null : post.toPostOutput();
    }

    @PutMapping("/addPost")
    public boolean addPost(@ModelAttribute PostInput post) {
        return postDao.addPost(post);
    }

    @PatchMapping("/updatePost")
    public boolean updatePost(@ModelAttribute PostInput postInput) {
        return postDao.updatePost(postInput);
    }

    @DeleteMapping("/deletePost")
    public boolean deletePost(int id){
        return postDao.deletePost(id);
    }

}
