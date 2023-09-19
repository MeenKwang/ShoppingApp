package com.shopping.admin.security;

import com.shopping.admin.repository.UserRepository;
import com.shopping.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userByEmail = userRepo.findByEmail(email);
        if(userByEmail != null) {
            return new CustomUserDetails(userByEmail);
        }
        throw new UsernameNotFoundException("Could not find user with email: " + email);
    }
}
