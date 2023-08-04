package com.Instagram.chatApp.services;

import com.Instagram.chatApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("load By Email ----------------------");
        Optional<com.Instagram.chatApp.models.User>opt = userRepository.findByEmail(username);
        if(opt.isPresent()){
            com.Instagram.chatApp.models.User user = opt.get();
            List<GrantedAuthority> authorities = new ArrayList<>();

            return new User(user.getEmail(),user.getPassword(),authorities);
        }
        throw new BadCredentialsException("User Not found with"+username);
    }
}
