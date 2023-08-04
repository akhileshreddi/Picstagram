package com.Instagram.chatApp.services;

import com.Instagram.chatApp.Exceptions.PostException;
import com.Instagram.chatApp.Exceptions.UserException;
import com.Instagram.chatApp.models.Post;
import com.Instagram.chatApp.models.User;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface PostService {

    public Post createPost(Post post, Integer userId) throws UserException;
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException;

    public List<Post> findPostByUserId(Integer userId) throws UserException;

    public Post findPostById(Integer postId) throws PostException;

    public List<Post> findAllPostByUserIds(List<Integer>userIds) throws UserException,PostException;

    public String savedPost(Integer postId, Integer userId) throws PostException,UserException;

    public String unsavedPost(Integer postId,Integer userId) throws PostException,UserException;

    public Post likepost(Integer postId, Integer userId) throws PostException,UserException;

    public Post unlikepost(Integer postId, Integer userId) throws PostException,UserException;

}
