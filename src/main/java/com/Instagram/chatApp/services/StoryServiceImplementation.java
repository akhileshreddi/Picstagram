package com.Instagram.chatApp.services;

import com.Instagram.chatApp.Exceptions.StoryException;
import com.Instagram.chatApp.Exceptions.UserException;
import com.Instagram.chatApp.Repository.StoryRepository;
import com.Instagram.chatApp.Repository.UserRepository;
import com.Instagram.chatApp.dto.UserDto;
import com.Instagram.chatApp.models.Story;
import com.Instagram.chatApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryServiceImplementation implements StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Story createStory(Story story, Integer userId) throws UserException, StoryException {
        User user = userService.findByUserId(userId);
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setUserImage(user.getImage());

        story.setUser(userDto);
        story.setTimeStamp(LocalDateTime.now());
        user.getStories().add(story);
        userRepository.save(user);
        return storyRepository.save(story);
    }

    @Override
    public List<Story> findStoriesByUserId(Integer userId) throws UserException, StoryException {

        User user = userService.findByUserId(userId);
        List<Story>stories = user.getStories();
        if(stories.size()==0){
            throw new StoryException("User Doesn't Have story");
        }

        return stories;
    }
}
