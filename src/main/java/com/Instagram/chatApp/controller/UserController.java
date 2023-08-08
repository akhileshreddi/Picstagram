package com.Instagram.chatApp.controller;

import com.Instagram.chatApp.Exceptions.UserException;
import com.Instagram.chatApp.Response.MessageResponse;
import com.Instagram.chatApp.models.User;
import com.Instagram.chatApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/id/{id}")
    public ResponseEntity<User>findUserByIdHandler(@PathVariable Integer id) throws UserException
    {
        User user = userService.findByUserId(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<User>findUserByUsernameHandler(@PathVariable String username) throws UserException
    {
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/m/{userIds}")
    public  ResponseEntity<List<User>>findUsersByUserIdsHandler(@PathVariable List<Integer>userIds) throws UserException
    {
        List<User>users = userService.findUserByIds(userIds);
        return new ResponseEntity<List<User>>(users,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserHandler(@RequestParam("q") String query) throws UserException
    {
        List<User>users = userService.searchUser(query);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @PutMapping("followUser/{userId}")
    public ResponseEntity<String> followUserHandler(@PathVariable("userId") Integer userId, @RequestHeader("Authorization") String token) throws UserException{
        User follwuser = userService.findByUserId(userId);
        User  regUser = userService.findUserProfile(token);

        String message = userService.followUser(regUser.getId(), follwuser.getId());

        return new ResponseEntity<String>(message, HttpStatus.OK);

    }

    @PutMapping("unfollowUser/{userId}")
    public ResponseEntity<String> unfollowUserHandler(@PathVariable("userId") Integer userId, @RequestHeader("Authorization") String token) throws UserException{
        User follwuser = userService.findByUserId(userId);
        User regUser = userService.findUserProfile(token);

        String message = userService.unFollowUser(regUser.getId(), follwuser.getId());

        return new ResponseEntity<String>(message, HttpStatus.OK);

    }



}
