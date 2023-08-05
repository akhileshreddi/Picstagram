package com.Instagram.chatApp.controller;

import com.Instagram.chatApp.Exceptions.PostException;
import com.Instagram.chatApp.Exceptions.UserException;
import com.Instagram.chatApp.models.Post;
import com.Instagram.chatApp.models.User;
import com.Instagram.chatApp.services.PostService;
import com.Instagram.chatApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPostHandler(@RequestBody Post post, @RequestHeader("Authorization") String token) throws PostException, UserException {
        User user = userService.findUserProfile(token);
        Post createdPost = postService.createPost(post, user.getId());

        return new ResponseEntity<Post>(createdPost, HttpStatus.OK);

    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<Post>> findPostsByUserIdHandler(@PathVariable("id") Integer userId) throws PostException,UserException{
        List<Post>posts = postService.findPostByUserId(userId);

        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);

    }

    @GetMapping("/following/{id}")
    public ResponseEntity<List<Post>> findAllPostsByUserIdsHandler(@PathVariable("ids") List<Integer>userIds) throws PostException,UserException{
        List<Post>posts = postService.findAllPostByUserIds(userIds);

        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);

    }

}
