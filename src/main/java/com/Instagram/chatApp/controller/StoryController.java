package com.Instagram.chatApp.controller;

import com.Instagram.chatApp.Exceptions.StoryException;
import com.Instagram.chatApp.Exceptions.UserException;
import com.Instagram.chatApp.models.Story;
import com.Instagram.chatApp.models.User;
import com.Instagram.chatApp.services.PostService;
import com.Instagram.chatApp.services.StoryService;
import com.Instagram.chatApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/story")
public class StoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private StoryService storyService;

    @Autowired
    private PostService postService;


    @PostMapping("/create")
    public ResponseEntity<Story> createStoryHandler(@RequestBody Story story,  @RequestHeader("Authorization") String token) throws UserException, StoryException{
        User user = userService.findUserProfile(token);
        Story createdStory = storyService.createStory(story , user.getId());
        return new ResponseEntity<Story>(createdStory, HttpStatus.OK);

    }

    @GetMapping("getStories/{userId}")
    public ResponseEntity<List<Story>> getStoriesUserHandler(@PathVariable("userId") Integer userId) throws UserException,StoryException{
        List<Story>stories = storyService.findStoriesByUserId(userId);

        return new ResponseEntity<>(stories, HttpStatus.OK);
    }
}
