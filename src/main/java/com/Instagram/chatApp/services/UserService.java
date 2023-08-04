package com.Instagram.chatApp.services;

import com.Instagram.chatApp.Exceptions.UserException;
import com.Instagram.chatApp.models.User;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    public User registeredUser(User user) throws UserException;
    public User findByUserId(Integer userId) throws UserException;
    public User findUserProfile(String token) throws UserException;
    public User findUserByUsername(String username) throws UserException;


    public String followUser(Integer regUserId, Integer followUserId) throws UserException;

    public String unFollowUser(Integer regUserId, Integer followUserId) throws UserException;

    public List<User>findUserByIds(List<Integer> userIds) throws UserException;

    public List<User> searchUser(String query) throws UserException;
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException;



}
