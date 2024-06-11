package com.test.oauth2.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class MyController {

    @GetMapping(value="/my")
    public String my(Model model) {

        //유저 정보 확인
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        model.addAttribute("username", username);
        model.addAttribute("role", role);
        model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());

        return "my";
    }


    @GetMapping(value="/login")
    public String login() {
        return "login";
    }



}
