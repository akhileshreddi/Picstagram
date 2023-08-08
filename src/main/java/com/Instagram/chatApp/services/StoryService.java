package com.Instagram.chatApp.services;

import com.Instagram.chatApp.Exceptions.StoryException;
import com.Instagram.chatApp.Exceptions.UserException;
import com.Instagram.chatApp.models.Story;

import java.util.List;

public interface StoryService {

    public Story createStory(Story story, Integer userId) throws UserException, StoryException;

    public List<Story> findStoriesByUserId(Integer userId) throws UserException, StoryException;
}
