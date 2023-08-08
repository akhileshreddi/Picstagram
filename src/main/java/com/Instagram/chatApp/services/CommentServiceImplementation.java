package com.Instagram.chatApp.services;

import com.Instagram.chatApp.Exceptions.CommentException;
import com.Instagram.chatApp.Exceptions.PostException;
import com.Instagram.chatApp.Exceptions.UserException;
import com.Instagram.chatApp.Repository.CommentRepository;
import com.Instagram.chatApp.Repository.PostRepository;
import com.Instagram.chatApp.Repository.UserRepository;
import com.Instagram.chatApp.dto.UserDto;
import com.Instagram.chatApp.models.Comment;
import com.Instagram.chatApp.models.Post;
import com.Instagram.chatApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImplementation implements CommentService {


    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;
    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException {
        User user = userService.findByUserId(userId);
        Post post = postService.findPostById(postId);

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUserImage(user.getImage());

        comment.setUser(userDto);
        comment.setCreatedAt(LocalDateTime.now());

        Comment newCreatedComment = commentRepository.save(comment);
        post.getComments().add(newCreatedComment);
        postRepository.save(post);

        return newCreatedComment;
    }

    @Override
    public Comment findCommentById(Integer commentId) throws CommentException {
        Optional<Comment>opt = commentRepository.findById(commentId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new CommentException("comment Not exist");
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException {
        User user = userService.findByUserId(userId);
        Comment comment = findCommentById(commentId);

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUserImage(user.getImage());

        comment.getLikedByUsers().add(userDto);
        Comment nwComment = commentRepository.save(comment);

        return nwComment;
    }

    @Override
    public Comment unlikeComment(Integer commentId, Integer userId) throws CommentException, UserException {
        User user = userService.findByUserId(userId);
        Comment comment = findCommentById(commentId);

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUserImage(user.getImage());

        comment.getLikedByUsers().remove(userDto);
        Comment nwComment = commentRepository.save(comment);

        return nwComment;
    }
}
