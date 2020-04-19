package com.example.restwithspringbootudemy.controller;

import com.example.restwithspringbootudemy.data.model.Permission;
import com.example.restwithspringbootudemy.repository.UserRepository;
import com.example.restwithspringbootudemy.security.AccountCredentialsVO;
import com.example.restwithspringbootudemy.security.jwt.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          UserRepository userRepository) {

        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "Authenticates a user and returns a token")
    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/signin",
            produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
        try {
            var username = data.getUsername();
            var pasword = data.getPassword();

            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pasword));

            if (!authenticate.isAuthenticated()) {
                throw new UsernameNotFoundException("Username " + username + " no authorized");
            }

             var token = "";

            if (authenticate != null) {
                token = jwtTokenProvider.createToken(username, authenticate.getAuthorities().stream().map(p -> ((Permission) p).getDescription()).collect(Collectors.toList()));
            } else {
                throw new UsernameNotFoundException("Username " + username + " not found!");
            }

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }

}
