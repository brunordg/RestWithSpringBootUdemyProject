package com.example.restwithspringbootudemy.services;

import com.example.restwithspringbootudemy.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var user = repository.findByUserName(s);

        if (user != null) {
            return user;
        }

        throw new UsernameNotFoundException("Username " + s + " not found");
    }
}
