package com.example.toDo.controller;


import com.example.toDo.Dto.AuthRequest;
import com.example.toDo.Dto.TokenDto;
import com.example.toDo.entity.ToDo;
import com.example.toDo.services.JwtService;

import com.example.toDo.services.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public TokenDto authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String s= authentication.getAuthorities().toString();
            TokenDto tokenDto=jwtService.generateToken(authRequest.getUsername());
            if(!authentication.getAuthorities().isEmpty())
            {
                tokenDto.setRoles(authentication.getAuthorities().toString());
            }
            return tokenDto;
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }


    }

}
