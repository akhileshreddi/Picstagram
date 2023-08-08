package com.Instagram.chatApp.services;

import com.Instagram.chatApp.Exceptions.CommentException;
import com.Instagram.chatApp.Exceptions.PostException;
import com.Instagram.chatApp.Exceptions.UserException;
import com.Instagram.chatApp.models.Comment;
import com.Instagram.chatApp.models.User;

public interface CommentService {

    public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException;
    public Comment findCommentById(Integer commentId) throws CommentException;

    public Comment likeComment(Integer commentId, Integer userId) throws CommentException,UserException;

    public Comment unlikeComment(Integer commentId,Integer userId) throws CommentException, UserException;

}
