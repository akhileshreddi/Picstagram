package com.Instagram.chatApp.services;

import com.Instagram.chatApp.Exceptions.PostException;
import com.Instagram.chatApp.Exceptions.UserException;
import com.Instagram.chatApp.Repository.PostRepository;
import com.Instagram.chatApp.Repository.UserRepository;
import com.Instagram.chatApp.dto.UserDto;
import com.Instagram.chatApp.models.Post;
import com.Instagram.chatApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Override
    public Post createPost(Post post, Integer userId) throws UserException {
        User user = userService.findByUserId(userId);
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        post.setUser(userDto);

        Post createdPost = postRepository.save(post);

        return createdPost;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findByUserId(userId);
        if(post.getUser().getId().equals(user.getId())){
            postRepository.deleteById(post.getId());
            return "Post Deleted Successfully";
        }

        throw new PostException("No access to Delete Post");

    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws UserException {
        List<Post> posts = postRepository.findByUserId(userId);
        if(posts.size()==0){
            throw new UserException("No posts for this user");
        }
        return posts;
    }

    @Override
    public Post findPostById(Integer postId) throws PostException {
        Optional<Post> opt = postRepository.findById(postId);
        if(opt.isPresent()){
            return opt.get();
        }

        throw new PostException("No Post is present this Id"+postId);
    }

    @Override
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws UserException, PostException {
        List<Post>posts = postRepository.findAllPostsByUserIds(userIds);
        System.out.println("Posts are -------"+posts);
        if(posts.size()==0){
            throw new PostException("No posts available");
        }
        return posts;
    }

    @Override
    public String savedPost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findByUserId(userId);
        if(!user.getSavedPost().contains(post)){
            user.getSavedPost().add(post);
            userRepository.save(user);
        }
        return "Post saved Successfully";
    }

    @Override
    public String unsavedPost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findByUserId(userId);
        if(!user.getSavedPost().contains(post)){
            user.getSavedPost().remove(post);
            userRepository.save(user);
        }
        return "Post Removed Successfully";
    }

    @Override
    public Post likepost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findByUserId(userId);
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        post.getLikedBy().add(userDto);

        return postRepository.save(post);
    }

    @Override
    public Post unlikepost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);
        User user = userService.findByUserId(userId);
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        post.getLikedBy().remove(userDto);

        return postRepository.save(post);
    }
}
