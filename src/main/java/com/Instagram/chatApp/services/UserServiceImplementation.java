package com.Instagram.chatApp.services;


import com.Instagram.chatApp.Exceptions.UserException;
import com.Instagram.chatApp.Repository.UserRepository;
import com.Instagram.chatApp.Security.JwtTokenClaims;
import com.Instagram.chatApp.Security.JwtTokenProvider;
import com.Instagram.chatApp.dto.UserDto;
import com.Instagram.chatApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public User registeredUser(User user) throws UserException {

        Optional<User>isEmailExist = userRepository.findByEmail(user.getEmail());
        if(isEmailExist.isPresent())
        {
            throw new UserException("Email already in Use");
        }
        Optional<User>isUsernameExist = userRepository.findByUsername(user.getUsername());
        if(isUsernameExist.isPresent())
        {
            throw new UserException("username already taken");
        }
        if(user.getEmail()==null || user.getPassword()==null || user.getUsername()==null || user.getName()==null)
        {
            throw new UserException("All fills are required");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));


        return userRepository.save(newUser);
    }

    @Override
    public User findByUserId(Integer userId) throws UserException {
        Optional<User>opt = userRepository.findById(userId);
        if(opt.isPresent())
        {
            return opt.get();
        }
        throw new UserException("User Not existed");
    }

    @Override
    public User findUserProfile(String token) throws UserException {
        token = token.substring(7);

        JwtTokenClaims jwtTokenClaims = jwtTokenProvider.getClaimsFromToken(token);

        String email = jwtTokenClaims.getUsername();
        Optional<User>opt = userRepository.findByEmail(email);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new UserException("Invalid Token------");
    }

    @Override
    public User findUserByUsername(String username) throws UserException {
        Optional<User>opt = userRepository.findByUsername(username);
        if(opt.isPresent())
        {
            return opt.get();
        }
        throw new UserException("User Not existed");
    }


    @Override
    public String followUser(Integer regUserId, Integer followUserId) throws UserException {

        User reqUser = findByUserId(regUserId);
        User follUser = findByUserId(followUserId);

        UserDto follower = new UserDto();

        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUserImage(reqUser.getUsername());
        follower.setUsername(reqUser.getUsername());

        UserDto following = new UserDto();

        following.setEmail(follUser.getEmail());
        following.setId(follUser.getId());
        following.setUsername(follUser.getUsername());
        following.setName(follUser.getName());
        following.setUserImage(follUser.getUsername());

        reqUser.getFollowing().add(following);
        follUser.getFollower().add(follower);

        userRepository.save(follUser);
        userRepository.save(reqUser);

        return "You are following  "+follUser.getUsername();

    }

    @Override
    public String unFollowUser(Integer regUserId, Integer followUserId) throws UserException {

        User reqUser = findByUserId(regUserId);
        User follUser = findByUserId(followUserId);

        UserDto follower = new UserDto();

        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUserImage(reqUser.getUsername());
        follower.setUsername(reqUser.getUsername());

        UserDto following = new UserDto();

        following.setEmail(follUser.getEmail());
        following.setId(follUser.getId());
        following.setUsername(follUser.getUsername());
        following.setName(follUser.getName());
        following.setUserImage(follUser.getUsername());

        reqUser.getFollowing().remove(following);
        follUser.getFollower().remove(follower);

        userRepository.save(follUser);
        userRepository.save(reqUser);

        return "You are Unfollowed "+follUser.getUsername();
    }

    @Override
    public List<User> findUserByIds(List<Integer> userIds) throws UserException {
        List<User> users = userRepository.findAllUserByUserIds(userIds);
        return users;
    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        List<User> user = userRepository.findByQuery(query);
        if(user.size()==0)
        {
            throw new UserException("User Not found");
        }
        return user;
    }

    @Override
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException {

        if(updatedUser.getEmail() != null){
            existingUser.setEmail(updatedUser.getEmail());
        }
        if(updatedUser.getBio() != null){
            existingUser.setBio(updatedUser.getBio());
        }
        if(updatedUser.getName() != null){
            existingUser.setName(updatedUser.getName());
        }
        if(updatedUser.getUsername() != null){
            existingUser.setUsername(updatedUser.getUsername());
        }
        if(updatedUser.getMobile() != null){
            existingUser.setMobile(updatedUser.getMobile());
        }
        if(updatedUser.getGender() != null){
            existingUser.setGender(updatedUser.getGender());
        }
        if(updatedUser.getWebsite() != null){
            existingUser.setWebsite(updatedUser.getWebsite());
        }

        if(updatedUser.getId().equals(existingUser.getId())){
            return userRepository.save(existingUser);
        }


        throw new UserException("Unable to Update User");
    }
}
