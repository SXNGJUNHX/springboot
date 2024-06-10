package com.test.jwt.controller;

import com.test.jwt.dto.MemberDTO;
import com.test.jwt.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value="/my")
    public String my() {
        
        //유저 정보 확인
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        return "MemberController >>>>> " + username + " " + role;
    }


    @PostMapping(value="/joinok")
    public String joinok(MemberDTO memberDTO) {

        System.out.println("dto >>>>>>>>>>> " + memberDTO);

        memberService.join(memberDTO);

        return "Join Success";
    }


}












