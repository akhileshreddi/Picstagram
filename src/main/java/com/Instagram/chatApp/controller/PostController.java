package com.Instagram.chatApp.controller;

import com.Instagram.chatApp.Exceptions.PostException;
import com.Instagram.chatApp.Exceptions.UserException;
import com.Instagram.chatApp.Response.MessageResponse;
import com.Instagram.chatApp.models.Post;
import com.Instagram.chatApp.models.User;
import com.Instagram.chatApp.services.PostService;
import com.Instagram.chatApp.services.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
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

    @GetMapping("/following/{ids}")
    public ResponseEntity<List<Post>> findAllPostsByUserIdsHandler(@PathVariable("ids") List<Integer>userIds) throws PostException,UserException{
        List<Post>posts = postService.findAllPostByUserIds(userIds);

        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);

    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable("postId") Integer postId) throws PostException,UserException{
        Post post = postService.findPostById(postId);
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable("postId") Integer postId, @RequestHeader("Authorization") String token) throws PostException,UserException{
        User user = userService.findUserProfile(token);
        Post likedPost = postService.likepost(postId,user.getId());
        return new ResponseEntity<>(likedPost,HttpStatus.OK);
    }

    @PutMapping("/unlike/{postId}")
    public ResponseEntity<Post> unlikePostHandler(@PathVariable("postId") Integer postId, @RequestHeader("Authorization") String token) throws PostException,UserException{
        User user = userService.findUserProfile(token);
        Post likedPost = postService.unlikepost(postId,user.getId());
        return new ResponseEntity<>(likedPost,HttpStatus.OK);
    }


    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePostHandler(@PathVariable("postId") Integer postId, @RequestHeader("Authorization") String token) throws PostException,UserException{
        User user = userService.findUserProfile(token);
        String message = postService.deletePost(postId,user.getId());
//        MessageResponse messageResponse = new MessageResponse(message);


        return new ResponseEntity<String>(message, HttpStatus.ACCEPTED);
    }

    @PutMapping("/savePost/{postId}")
    public ResponseEntity<String> savePostHandler(@PathVariable("postId") Integer postId, @RequestHeader("Authorization") String token) throws PostException,UserException{
        User user = userService.findUserProfile(token);
        String message = postService.savedPost(postId,user.getId());


//        MessageResponse messageResponse = new MessageResponse(message);
//        System.out.println(messageResponse);

        return new ResponseEntity<String>(message,HttpStatus.OK);
    }

    @PutMapping("/unSavePost/{postId}")
    public ResponseEntity<String> unSavePostHandler(@PathVariable("postId") Integer postId, @RequestHeader("Authorization") String token) throws PostException,UserException{
        User user = userService.findUserProfile(token);
        String message = postService.unsavedPost(postId,user.getId());

//        MessageResponse messageResponse = new MessageResponse(message);

        return new ResponseEntity<String>(message,HttpStatus.OK);
    }

}
