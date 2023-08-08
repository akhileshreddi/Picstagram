package com.Instagram.chatApp.controller;

import com.Instagram.chatApp.Exceptions.CommentException;
import com.Instagram.chatApp.Exceptions.PostException;
import com.Instagram.chatApp.Exceptions.UserException;
import com.Instagram.chatApp.models.Comment;
import com.Instagram.chatApp.models.User;
import com.Instagram.chatApp.services.CommentService;
import com.Instagram.chatApp.services.PostService;
import com.Instagram.chatApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;


    @PostMapping("/createComment/{postId}")
    public ResponseEntity<Comment> createCommentHandler(@RequestBody Comment comment,@PathVariable("postId") Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException,CommentException {
        User user = userService.findUserProfile(token);
        Comment createdComment = commentService.createComment(comment,postId,user.getId());

        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }

    @PutMapping("/like/{comId}")
    public ResponseEntity<Comment>likeCommentHandler(@PathVariable("comId") Integer commentId, @RequestHeader("Authorization") String token) throws UserException,CommentException{
        User user = userService.findUserProfile(token);
        Comment comment = commentService.likeComment(commentId,user.getId());

        return new ResponseEntity<>(comment, HttpStatus.OK);

    }

    @PutMapping("/unlike/{comId}")
    public ResponseEntity<Comment>UnlikeCommentHandler(@PathVariable("comId") Integer commentId, @RequestHeader("Authorization") String token) throws UserException,CommentException{
        User user = userService.findUserProfile(token);
        Comment comment = commentService.unlikeComment(commentId,user.getId());

        return new ResponseEntity<>(comment, HttpStatus.OK);

    }





}
